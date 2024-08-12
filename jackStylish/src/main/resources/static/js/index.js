// let proxyUrl = "https://cors-anywhere.herokuapp.com/";

document.addEventListener("DOMContentLoaded", () => {

    let apiUrl = `http://54.248.138.109/api/1.0/marketing/campaigns`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            const campaigns = data.data;
            if (campaigns.length > 0) {
                const firstCampaign = campaigns[0];
                displayCampaign(firstCampaign);
            }
        })
        .catch(error => console.error('Error fetching campaigns:', error));

    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category');

    if (category) {
        fetchProducts(category);
    } else {
        fetchProducts("all");
    }
});

function displayCampaign(campaign) {
    const campaignBlock = document.getElementById('campaign-block');
    campaignBlock.innerHTML = `
        <a class="campaign" href="product.html?id=${campaign.product_id}" style="background-image: url(${campaign.picture});">
            <div class="campaign-text">
                ${campaign.story}
            </div>
        </a>
    `;
}

function fetchProducts(category) {
    const apiVersion = '1.0';
    let apiUrl = '';

    if (category === "all") {
        apiUrl = `http://54.248.138.109/api/${apiVersion}/products/all`;
    } else {
        apiUrl = `http://54.248.138.109/api/${apiVersion}/products/${category}`;
    }

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            displayProducts(data.data);
        })
        .catch(error => {
            console.error('錯誤:', error);
            document.getElementById('product-list').innerText = '無法載入產品資料。';
        });
}

function displayProducts(products) {
    const productList = document.getElementById('product-list');
    productList.innerHTML = '';

    if (products && Array.isArray(products)) {
        products.forEach(product => {
            const productItem = document.createElement('div');

            //set will delete repeated elements
            const uniqueColors = [...new Set(product.variants.map(variant => variant.color_code))];

            const colorBoxes = uniqueColors.length > 0
                ? uniqueColors.map(color => `<div class="color-box" style="background-color: ${color};"></div>`).join('')
                : '<p>顏色: 無</p>';

            productItem.innerHTML = `
                <div>
                    <a href="product.html?id=${product.id}">
                    <img src="${product.main_image}" alt="${product.title}" style="width: 360px; height: 480px;">
                        <div>
                            ${colorBoxes}
                        </div>
                        <p>
                            價格: ${product.price} 元
                        </p>
                        <h2>${product.title}</h2>
                    </a>
                </div>
            `;
            productList.appendChild(productItem);
        });
    } else {
        productList.innerText = '沒有產品可顯示。';
    }
}
