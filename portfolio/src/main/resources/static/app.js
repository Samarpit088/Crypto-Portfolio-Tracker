const BASE_URL = "";


// REGISTER
const registerForm = document.getElementById("registerForm");

if (registerForm) {
    registerForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const data = {
            name: document.getElementById("registerName").value,
            email: document.getElementById("registerEmail").value,
            password: document.getElementById("registerPassword").value
        };

        const response = await fetch(`${BASE_URL}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        document.getElementById("message").innerText = result.message;
    });
}


// LOGIN
const loginForm = document.getElementById("loginForm");

if (loginForm) {
    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const data = {
            email: document.getElementById("loginEmail").value,
            password: document.getElementById("loginPassword").value
        };

        const response = await fetch(`${BASE_URL}/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        localStorage.setItem("token", result.token);

        window.location.href = "dashboard.html";
    });
}


// ADD HOLDING
const holdingForm = document.getElementById("holdingForm");

if (holdingForm) {
    holdingForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const token = localStorage.getItem("token");

        const data = {
            coinName: document.getElementById("coinName").value,
            symbol: document.getElementById("symbol").value,
            quantityHeld: document.getElementById("quantityHeld").value,
            buyPrice: document.getElementById("buyPrice").value,
            buyDate: document.getElementById("buyDate").value
        };

        await fetch(`${BASE_URL}/portfolio/add`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify(data)
        });

        loadPortfolio();
    });
}


// LOAD PORTFOLIO
async function loadPortfolio() {

    const token = localStorage.getItem("token");

    const response = await fetch(`${BASE_URL}/portfolio/my`, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });

    const holdings = await response.json();

    const container = document.getElementById("portfolioContainer");

    container.innerHTML = "";

    holdings.forEach(h => {

        container.innerHTML += `
            <div class="card">
                <h3>${h.coinName} (${h.symbol})</h3>
                <p>Quantity: ${h.quantityHeld}</p>
                <p>Buy Price: ${h.buyPrice}</p>
                <p>Current Price: ${h.currentPrice}</p>
                <p>Current Value: ${h.currentValue}</p>
                <p>Profit/Loss: ${h.profitLoss}</p>
            </div>
        `;
    });
}
