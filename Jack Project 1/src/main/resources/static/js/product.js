let selectedColor;
let selectedSize;

const decrease = document.getElementById('decrease');
const increase = document.getElementById('increase');
const quantity = document.getElementById('product-quantity');

let prime;

document.addEventListener('DOMContentLoaded', function () {
    // get id from URL
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    let apiUrl = `http://54.248.138.109/api/1.0/products/details?id=${id}`;

    if (id) {
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                const product = data.data;

                document.getElementById('product-title').textContent = product.title;
                document.getElementById('product-id').textContent = product.id;
                document.getElementById('product-price').textContent = `TWD. ${product.price}`;

                document.getElementById('product-note').textContent = `${product.note}`;
                document.getElementById('product-texture').textContent = `${product.texture}`;
                document.getElementById('product-description').textContent = product.description;
                document.getElementById('product-place').textContent = `產地: ${product.place}`;

                document.getElementById('product-story').textContent = `${product.story}`;
                document.getElementById('product-image').src = product.main_image;

                const imagesContainer = document.getElementById('product-images');
                product.images.forEach(imageUrl => {
                    const img = document.createElement('img');
                    img.src = imageUrl;
                    imagesContainer.appendChild(img);
                });

                const colorsList = document.getElementById('product-colors');
                product.colors.forEach(color => {
                    const listItem = document.createElement('button');
                    listItem.className = "color-box";
                    listItem.style.backgroundColor = color.code;
                    colorsList.appendChild(listItem);
                });

                const colorButtons = document.querySelectorAll('button.color-box');
                const sizeButtons = document.querySelectorAll('button.size-box');

                colorButtons.forEach(button => {
                    button.addEventListener('click', () => {
                        // remove all button selected status
                        colorButtons.forEach(btn => btn.classList.remove('selected'));

                        button.classList.add('selected');

                        checkSelection();

                        const selectedColorBox = document.querySelector('.color-box.selected');
                        selectedColor = rgbToHex(window.getComputedStyle(selectedColorBox).backgroundColor);

                        const selectedColorVariants = product.variants.filter(v => v.color_code === selectedColor);
                        const availableSizes = selectedColorVariants
                            .filter(v => v.stock > 0)
                            .map(v => v.size);

                        sizeButtons.forEach(sizeButton => {
                            if (availableSizes.includes(sizeButton.textContent)) {
                                sizeButton.disabled = false;
                            } else {
                                sizeButton.disabled = true;
                            }
                        })

                    });
                });

                sizeButtons.forEach(button => {
                    button.addEventListener('click', () => {
                        sizeButtons.forEach(btn => btn.classList.remove('selected'));

                        button.classList.add('selected');

                        checkSelection();

                        selectedSize = document.querySelector('.size-box.selected').textContent;

                    });
                });

                decrease.addEventListener('click', () => {

                    if (parseInt(quantity.textContent, 10) >= 1) {
                        quantity.textContent = (parseInt(quantity.textContent, 10) - 1).toString();
                        checkSelection();
                    }

                });

                increase.addEventListener('click', () => {

                    const selectedVariant = product.variants.find(v => v.color_code === selectedColor && v.size === selectedSize);

                    if (parseInt(quantity.textContent, 10) < selectedVariant.stock) {
                        quantity.textContent = (parseInt(quantity.textContent, 10) + 1).toString();
                        checkSelection();
                    }

                });

                document.getElementById("checkout").addEventListener("click", function () {
                    if (localStorage.getItem('access_token')) {
                        document.getElementById("payment").style.display = "block";
                    } else {
                        alert("請先登入");
                        window.location.href = "profile.html";
                    }

                });

                TPDirect.setupSDK(12348, 'app_pa1pQcKoY22IlnSXq5m5WP5jFKzoRG58VEXpT7wU62ud7mMbDOGzCYIlzzLF', 'sandbox')
                TPDirect.card.setup({
                    fields: {
                        number: {
                            element: '.form-control.card-number',
                            placeholder: '**** **** **** ****'
                        },
                        expirationDate: {
                            element: document.getElementById('tappay-expiration-date'),
                            placeholder: 'MM / YY'
                        },
                        ccv: {
                            element: $('.form-control.ccv')[0],
                            placeholder: '後三碼'
                        }
                    },
                    styles: {
                        'input': {
                            'color': 'gray'
                        },
                        'input.ccv': {
                            // 'font-size': '16px'
                        },
                        ':focus': {
                            'color': 'black'
                        },
                        '.valid': {
                            'color': 'green'
                        },
                        '.invalid': {
                            'color': 'red'
                        },
                        '@media screen and (max-width: 400px)': {
                            'input': {
                                'color': 'orange'
                            }
                        }
                    },
                    // 此設定會顯示卡號輸入正確後，會顯示前六後四碼信用卡卡號
                    isMaskCreditCardNumber: true,
                    maskCreditCardNumberRange: {
                        beginIndex: 6,
                        endIndex: 11
                    }
                })
                // listen for TapPay Field
                TPDirect.card.onUpdate(function (update) {
                    /* Disable / enable submit button depend on update.canGetPrime  */
                    /* ============================================================ */

                    // update.canGetPrime === true
                    //     --> you can call TPDirect.card.getPrime()
                    // const submitButton = document.querySelector('button[type="submit"]')
                    if (update.canGetPrime) {
                        // submitButton.removeAttribute('disabled')
                        $('button[type="submit"]').removeAttr('disabled')
                    } else {
                        // submitButton.setAttribute('disabled', true)
                        $('button[type="submit"]').attr('disabled', true)
                    }


                    /* Change card type display when card type change */
                    /* ============================================== */

                    // cardTypes = ['visa', 'mastercard', ...]
                    var newType = update.cardType === 'unknown' ? '' : update.cardType
                    $('#cardtype').text(newType)


                    /* Change form-group style when tappay field status change */
                    /* ======================================================= */

                    // number 欄位是錯誤的
                    if (update.status.number === 2) {
                        setNumberFormGroupToError('.card-number-group')
                    } else if (update.status.number === 0) {
                        setNumberFormGroupToSuccess('.card-number-group')
                    } else {
                        setNumberFormGroupToNormal('.card-number-group')
                    }

                    if (update.status.expiry === 2) {
                        setNumberFormGroupToError('.expiration-date-group')
                    } else if (update.status.expiry === 0) {
                        setNumberFormGroupToSuccess('.expiration-date-group')
                    } else {
                        setNumberFormGroupToNormal('.expiration-date-group')
                    }

                    if (update.status.ccv === 2) {
                        setNumberFormGroupToError('.ccv-group')
                    } else if (update.status.ccv === 0) {
                        setNumberFormGroupToSuccess('.ccv-group')
                    } else {
                        setNumberFormGroupToNormal('.ccv-group')
                    }
                })

                $('form').on('submit', function (event) {
                    event.preventDefault()

                    // fix keyboard issue in iOS device
                    forceBlurIos()

                    const tappayStatus = TPDirect.card.getTappayFieldsStatus()
                    console.log(tappayStatus)

                    // Check TPDirect.card.getTappayFieldsStatus().canGetPrime before TPDirect.card.getPrime
                    if (tappayStatus.canGetPrime === false) {
                        alert('can not get prime')
                        return
                    }

                    // Get prime
                    TPDirect.card.getPrime(function (result) {
                        if (result.status !== 0) {
                            alert('get prime error ' + result.msg)
                            return
                        }
                        // alert('get prime 成功，prime: ' + result.card.prime)
                        prime = result.card.prime;

                        console.log(localStorage.getItem('access_token'));

                        let token = localStorage.getItem('access_token');


                        fetch('http://54.248.138.109/api/1.0/order/checkout', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                                'Authorization': 'Bearer ' + token
                            },
                            body: JSON.stringify({
                                "prime": prime,
                                "order": {
                                    "shipping": "delivery",
                                    "payment": "credit_card",
                                    "subtotal": 1234,
                                    "freight": 14,
                                    "total": product.price,
                                    "recipient": {
                                        "name": "Luke",
                                        "phone": "0987654321",
                                        "email": "luke@gmail.com",
                                        "address": "市政府站",
                                        "time": "morning"
                                    },
                                    "list": [
                                        {
                                            "id": product.id.toString(),
                                            "name": product.title.toString(),
                                            "price": product.price,
                                            "color": {
                                                "code": selectedColor.toString(),
                                                "name": product.colors.find(c => c.code === selectedColor).name.toString()
                                            },
                                            "size": selectedSize.toString(),
                                            "qty": 0
                                            // "qty": parseInt(quantity.textContent, 10)
                                        }
                                    ]
                                }
                            })
                        })
                            .then(response => response.json())
                            .then(data => {
                                console.log(data);
                                window.location.href = "thankyou.html"
                            })
                            .catch(error => {
                                console.error('Error:', error);

                                console.log(prime);

                                console.log(product.id.toString());
                                console.log(product.title.toString());
                                console.log(product.price);
                                console.log(selectedColor.toString());
                                console.log(product.colors.find(c => c.code === selectedColor).name.toString());
                                console.log(selectedSize.toString());
                                console.log(parseInt(quantity.textContent, 10));
                            })

                        document.getElementById("payment").addEventListener("submit", function (event) {
                            event.preventDefault();


                        });
                    })
                })

                function setNumberFormGroupToError(selector) {
                    $(selector).addClass('has-error')
                    $(selector).removeClass('has-success')
                }

                function setNumberFormGroupToSuccess(selector) {
                    $(selector).removeClass('has-error')
                    $(selector).addClass('has-success')
                }

                function setNumberFormGroupToNormal(selector) {
                    $(selector).removeClass('has-error')
                    $(selector).removeClass('has-success')
                }

                function forceBlurIos() {
                    if (!isIos()) {
                        return
                    }
                    var input = document.createElement('input')
                    input.setAttribute('type', 'text')
                    // Insert to active element to ensure scroll lands somewhere relevant
                    document.activeElement.prepend(input)
                    input.focus()
                    input.parentNode.removeChild(input)
                }

                function isIos() {
                    return /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
                }


            })
            .catch(error => {
                console.error('Error fetching product details:', error);
            });
    } else {
        console.error('Product ID is missing in the URL.');
    }

});

function checkSelection() {
    const selectedItems = document.querySelectorAll('.selected').length;
    const button = document.getElementById('checkout');
    if (selectedItems >= 2 && parseInt(quantity.textContent, 10) > 0) {
        button.disabled = false;
    } else {
        button.disabled = true;
    }
}

function rgbToHex(rgb) {

    rgb = rgb.replace(/^rgba?\(|\s+|\)$/g, '').split(',');

    let r = parseInt(rgb[0], 10);
    let g = parseInt(rgb[1], 10);
    let b = parseInt(rgb[2], 10);

    return `#${[r, g, b].map(x => x.toString(16).padStart(2, '0')).join('').toUpperCase()}`;
}


