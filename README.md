# 💼 Smart Trading & Wallet Management System

A full-featured **JavaFX-based desktop application** for cryptocurrency trading, asset management, and real-time market visualization.

---

## 🚀 Features

### 🔐 User Management
- User signup/login with role-based access
- Account freezing/unfreezing (admin control)

### 💰 Wallet Management
- Deposit / Withdraw / Transfer funds
- Wallets linked via unique account numbers

### 📊 Portfolio Tracking
- View crypto holdings with real-time prices
- Store and manage quantity of assets
- Graphs per coin in **Wallet View**

### 🧠 Real-time Market Dashboard
- Live price charts of top 10 cryptocurrencies
- Switchable coin graph panel with single API call batching
- Coin-specific Buy/Sell actions

### 📈 Trading System
- Execute trades (buy/sell)
- Record of each trade with price at time

### 🔄 Transactions
- Full transaction history
- Tracks type (deposit, withdrawal, transfer, trade)
- Linked to unique account numbers

---

## 🗃️ Database Design

- Normalized schema with relational integrity
- Tables: User, Wallet, Asset (Crypto/Stock), Trade, Transaction, Portfolio, PortfolioAsset
- Portfolio holds `HashMap<AssetSymbol, Quantity>` through join table
- All financial logic based on `accountNumber`, not just userId

---

## ⚙️ Tech Stack

- **Java 17** with **JavaFX**
- **Maven** for dependency management
- **PostgreSQL** as backend database
- **CoinGecko API** (and fallback to CryptoCompare) for live prices
- **DAO Pattern**, **MVC Structure**, and **Multi-threaded Scheduled API fetch**

---

## 🧠 Learning Goals

This project aims to:

- Practice **JavaFX GUI**, real-time **API integration**, and **multi-threading**
- Deepen understanding of **relational databases**
- Transition into **DSA-based optimization** for asset search, filter, and transaction analysis

---

## 📌 Status

✅ Most modules complete  
🔄 Currently refining backend logic and transaction understanding  
📊 Upcoming: Use DSA to optimize transaction/asset lookup  

---

## 🙋‍♂️ Author

**Junaid Ashraf**  
Software Engineering Student @ COMSATS  
Java | OOP | JavaFX | PostgreSQL | APIs | DSA-in-Progress  

Connect with me on [LinkedIn](https://www.linkedin.com/in/junaid-ashraf56/)

