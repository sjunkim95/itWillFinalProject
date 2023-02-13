/**
 * 
 */

let carts = document.querySelectorAll('.add-cart')

let products = [
    {
        name: 'peperoni-pizza',
        tag: 'peperoni-pizza',
        price: 17000,
        inCart: 0
    },

    {
        name: 'cheese-pizza',
        tag: 'cheese-pizza',
        price: 15000,
        inCart: 0
    },

    {
        name: 'italy-margherita',
        tag: 'italy-margherita',
        price: 20000,
        inCart: 0
    }
]

for (let i = 0; i < carts.length; i++) {
    carts[i].addEventListener('click', () => {
        cartNumbers(products[i]);
        totalCost(products[i])
    })
}

function onLoadCartNumbers() {
    let productNumbers = localStorage.getItem('cartNumbers')

    if (productNumbers) {
        document.querySelector('.cart span').textContent = productNumbers;
    }
}

function cartNumbers(product) {
    console.log("The product clicked is...", product)
    let productNumbers = localStorage.getItem('cartNumbers')

    productNumbers = parseInt(productNumbers)

    if (productNumbers) {
        localStorage.setItem('cartNumbers', productNumbers + 1);
        document.querySelector('.cart span').textContent = productNumbers + 1;
    } else {
        localStorage.setItem('cartNumbers', 1);
        document.querySelector('.cart span').textContent = 1;
    }

    setItems(product);
}

function setItems(product) {
    let cartItems = localStorage.getItem('productsInCart');
    cartItems = JSON.parse(cartItems);

    if (cartItems != null) {
        if (cartItems[product.tag] == undefined) {
            cartItems = {
                ...cartItems,
                [product.tag]: product
            }
        }
        cartItems[product.tag].inCart += 1;
    } else {
        product.inCart = 1;
        cartItems = {
            [product.tag]: product
        }
    }

    localStorage.setItem("productsInCart", JSON.stringify(cartItems));
}

function totalCost(product) {

    let cartCost = localStorage.getItem('totalCost');

    console.log("Cartcost is", cartCost);
    console.log(typeof cartCost);
    console.log(typeof product.price);
    if (cartCost != null) {
        cartCost = parseInt(cartCost);
        localStorage.setItem("totalCost", cartCost + product.price);
    } else {
        localStorage.setItem("totalCost", product.price);
    }
}

function displayCart() {
    let cartItems = localStorage.getItem("productsInCart");
    cartItems = JSON.parse(cartItems);
    let productContainer = document.querySelector(".products");
    let cartCost = localStorage.getItem('totalCost');
    
    

    console.log("displayCart 안에 포함된 객체", cartItems);

    if (cartItems && productContainer) {
        productContainer.innerHTML = '';
        Object.values(cartItems).map(item => {
            
            productContainer.innerHTML +=
                `<div class ='all-warrper'>`
                + `<div class="product-wrapper">`
                + `<div class="product">`
                + `<ion-icon name="close-circle-outline" class ='remove-btn'></ion-icon>`
                + `<img src="/img/${item.tag}.jpg">`
                + `<span>${item.name}</span>`
                + `</div>`
                + `<div class="price">${item.price}</div>`
                + `<div class="quantity">`
                + `<ion-icon name="chevron-back-circle-outline" class='minus-btn'></ion-icon>`
                + `<span  class ="quantity-value">${item.inCart}</span>`
                + `<ion-icon name="chevron-forward-circle-outline"class = 'plus-btn'></ion-icon>`
                + `</div>`
                + `<div class="total">`
                + `${item.inCart * item.price}`
                + `</div>`
                + `</div>`
                + `</div>`


        });

        productContainer.innerHTML += `
            <div class="basketTotalContainer">
                <h4 class="basketTotalTitle">
                    Basket Total
                </h4>
                <h4 class="basketTotal">
                    ${cartCost}
                </h4>
            </div>
            <div class="btn-box">
                <button class="buy-btn">Buy Now</button>
            </div>
        `
    }
    manageQuantity();
    deleteButtons();

}

function showCart() {
    let productNumbers = localStorage.getItem('cartNumbers');
    let cartItems = localStorage.getItem("productsInCart");
    cartItems = JSON.parse(cartItems);

    let cartCost = localStorage.getItem('totalCost');
    let cartContainer = document.querySelector(".cartItem");


    if (productNumbers == null) {
        document.getElementById('cartItem').innerHTML = "장바구니가 비었습니다";
        document.getElementById("cartTotal").innerHTML = `0`;
    } else {

        if (cartItems && cartContainer) {
            cartContainer.innerHTML = '';
            Object.values(cartItems).map(_item => {
                cartContainer.innerHTML +=
                    `
            <div class='cart-item'>
               <div class='row-img'>
                    <img class='rowing' src="/img/${_item.tag}.jpg">
                </div>
                <div>
                    <p style='font-size:12px;'>${_item.name}</p>
                </div>
                <div>
                    <h2 style='font-size: 15px;'>가격: ${_item.price * _item.inCart}</h2>
                </div>
                <div>
                    <h2 style='font-size: 15px;'>수량: ${_item.inCart}</h2>
                </div>
                <div>
                    <ion-icon class="delete" name="close-circle-outline"></ion-icon>
                </div>
            `

                document.getElementById("cartTotal").innerHTML = cartCost;

            });
        }
    }
}
showCart();
onLoadCartNumbers();
displayCart();


function newBasketTotal() {
    let allTotal = document.querySelectorAll('.total');
    let newTotalTotal = 0;
    for (let i = 1; i < allTotal.length ; i++) {
        let eachTotal = Number(allTotal[i].innerHTML);
        newTotalTotal += eachTotal;
    }
    
    let basketTotal = document.querySelector('.basketTotal');
    localStorage.setItem("totalCost", newTotalTotal);
    basketTotal.innerHTML = newTotalTotal;
}




function addQuantity(){
    let cartItems = localStorage.getItem("productsInCart");
    cartItems = JSON.parse(cartItems);
    let allTotal = document.querySelectorAll('.total');
    let Quantity = document.querySelector('.quantity-value').innerHTML;
    for (let i = 0; i < allTotal.length-1 ; i++) {
        Object.values(cartItems).map(item => {
            if(item.tag == products[i].tag){
                console.log("cartItems는", cartItems["peperoni-pizza"].inCart)
                console.log("하나씩 제발", item.tag)
                console.log("하나증가!", Quantity)
                cartItems["italy-margherita"].inCart = Quantity;
                               
            }
        });

    }
    localStorage.setItem("productsInCart", JSON.stringify(cartItems));
}
// 
function manageQuantity() {
    let decreaseButtons = document.querySelectorAll('.minus-btn');
    let increaseButtons = document.querySelectorAll('.plus-btn');
    let cartItems = localStorage.getItem('productsInCart');
    let currentQuantity = 0;
    let currentProduct = "";
    cartItems = JSON.parse(cartItems);
    console.log(cartItems);

    for(let i=0; i < decreaseButtons.length; i++) {
        decreaseButtons[i].addEventListener('click', () => {
            currentQuantity = decreaseButtons[i].parentElement.querySelector('span').textContent;
            console.log(currentQuantity);
            currentProduct = decreaseButtons[i].parentElement.previousElementSibling.previousElementSibling.querySelector('span').textContent.toLowerCase().replace(/ /g, '').trim();
            console.log(currentProduct);

            if( cartItems[currentProduct].inCart > 1 ) {
                cartItems[currentProduct].inCart -= 1;
                cartNumbers( cartItems[currentProduct], "decrease" );
                totalCost( cartItems[currentProduct], "decrease" );
                localStorage.setItem('productsInCart', JSON.stringify(cartItems));
                displayCart();
            }
        });
    }
    //cartItems["italy-margherita"].inCart = Quantity;
    //cartItems[product.tag].inCart += 1;
    for(let i=0; i < increaseButtons.length; i++) {
        increaseButtons[i].addEventListener('click', () => {
            console.log("Increase button");
            currentQuantity = increaseButtons[i].parentElement.querySelector('span').textContent;
            console.log(currentQuantity);

            currentProduct = increaseButtons[i].parentElement.previousElementSibling.previousElementSibling.querySelector('span').textContent.toLowerCase().replace(/ /g, '').trim();
            console.log(currentProduct);
            console.log("이거는", cartItems[currentProduct]);
            
            cartItems[currentProduct].inCart += 1;
            cartNumbers( cartItems[currentProduct]);
            totalCost( cartItems[currentProduct]);
            localStorage.setItem('productsInCart', JSON.stringify(cartItems));
            displayCart();
            
        })
    }
}

function deleteButtons() {
    let deleteButtons = document.querySelectorAll('.product ion-icon');
    let productName;
    let productNumbers = localStorage.getItem('cartNumbers');
    let cartItems = localStorage.getItem('productsInCart');
    cartItems = JSON.parse(cartItems);
    let cartCost = localStorage.getItem('totalCost');
    


    for(let i=0; i < deleteButtons.length; i++) {
        deleteButtons[i].addEventListener('click', () => {
            productName = deleteButtons[i].parentElement.textContent.trim().toLowerCase().replace(/ /g, '');
            // console.log(productName);
            // console.log(cartItems[productName].name + " " + cartItems[productName].inCart)
            localStorage.setItem('cartNumbers', productNumbers - cartItems[productName].inCart );

            localStorage.setItem('totalCost', cartCost - ( cartItems[productName].price * cartItems[productName].inCart));

            delete cartItems[productName];
            localStorage.setItem('productsInCart', JSON.stringify(cartItems));

            displayCart();
            onLoadCartNumbers();
        });
    }
}
