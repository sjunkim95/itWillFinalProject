/**
 * 
 */
 
 let carts = document.querySelectorAll('.add-cart')
 
 let products = [
    {
        name: '페페로니 피자',
        tag: 'peperoni-pizza',
        price: 17000,
        inCart: 0
    },
    
    {
        name: '치즈 피자',
        tag: 'cheese-pizza',
        price: 15000,
        inCart: 0
    },
    
    {
        name: '마르게리따 피자',
        tag: 'italy-margherita',
        price: 20000,
        inCart: 0
    }
]
 
 for(let i=0; i < carts.length; i++) {
    carts[i].addEventListener('click', () =>{
        cartNumbers(products[i]);
        totalCost(products[i])
    })
}

function onLoadCartNumbers(){
    let productNumbers = localStorage.getItem('cartNumbers')
    
    if(productNumbers){
        document.querySelector('.cart span').textContent = productNumbers;
    }
}

function cartNumbers(product){
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

function setItems(product){
    let cartItems = localStorage.getItem('productsInCart');
    cartItems = JSON.parse(cartItems);
    
    if(cartItems != null){
        if(cartItems[product.tag] == undefined){
            cartItems = {
                ...cartItems,
                [product.tag]:product
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

function totalCost(product){
    
    let cartCost = localStorage.getItem('totalCost');
    
    console.log("Cartcost is", cartCost);
    console.log(typeof cartCost);
    console.log(typeof product.price);
    if(cartCost != null){
        cartCost = parseInt(cartCost);
        localStorage.setItem("totalCost", cartCost + product.price);
    } else {
        localStorage.setItem("totalCost", product.price);
    }
}

function displayCart(){
    let cartItems = localStorage.getItem("productsInCart");
    cartItems = JSON.parse(cartItems);
    let productContainer = document.querySelector(".products");
    let cartCost = localStorage.getItem('totalCost');
    
    console.log(cartItems);
    
    if( cartItems && productContainer ){
        productContainer.innerHTML = '';
        Object.values(cartItems).map(item => {
            productContainer.innerHTML += 
            `<div class="product-wrapper">`
                +`<div class="product">`
                    +`<ion-icon name="close-circle-outline"></ion-icon>`
                    +`<img src="/img/${item.tag}.jpg">`
                    +`<span>${item.name}</span>`
                +`</div>`
                +`<div class="price">${item.price}</div>`
                +`<div class="quantity">`
                    +`<ion-icon name="chevron-back-circle-outline"></ion-icon>`
                    +`<span>${item.inCart}</span>`
                    +`<ion-icon name="chevron-forward-circle-outline"></ion-icon>`
                +`</div>`
                +`<div class="total">`
                    +`${item.inCart * item.price}`
                +`</div>`
            +`</div>`
            
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
}

function showCart(){
    let productNumbers = localStorage.getItem('cartNumbers');
    let cartItems = localStorage.getItem("productsInCart");
    cartItems = JSON.parse(cartItems);
    
    let cartCost = localStorage.getItem('totalCost');
    let cartContainer = document.querySelector(".cartItem");
    
    console.log();

    if(productNumbers == null){
        document.getElementById('cartItem').innerHTML = "장바구니가 비었습니다";
        document.getElementById("cartTotal").innerHTML = `0`;
    } else {    
        
        if( cartItems && cartContainer ){
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
                    <h2 style='font-size: 15px;'>가격: ${_item.price*_item.inCart}</h2>
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