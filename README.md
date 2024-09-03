# CryptoExchangeProject

CryptoExchangeProject is a Globant 2024 bootcamp project. It allows users to buy and sell cryptocurrencies using a exchange system. This project follows a Model-View-Controller (MVC) architecture.

## Requirements

- **JDK 17**: The project requires JDK 17 to run.
- **Maven**: The project uses Maven for dependency management.
- **IDE**: Recommended IDE IntelliJ IDEA.

## Installation

### Option 1: Clone the repository
1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/CryptoExchangeProject.git
   ```
2. **Navigate to the project directory**:
   ```bash
   cd CryptoExchangeProject
   ```
### Option 2: Downdload the last version .zip file 
1. **Go to Releases**:
   - Download the [last version V3.0.0](https://github.com/JulioRogers/CryptoExchangeProject/releases/tag/v3.0.0)


### Setup in IntelliJ IDEA
1. **Open the project in IntelliJ IDEA**:
   - Select `File > Open...` and choose the project directory.
2. **Run the application**:
   - Right-click on the `CryptoExchangeApp.java` file and select `Run 'CryptoExchangeApp.main()'`.

## Usage

- **Register**: Create a new account by providing your name, email, and password.
- **Login**: Access your account using your registered email and password.
- **Deposit Money**: Add fiat money to your wallet.
- **Buy Cryptocurrency**: Purchase cryptocurrency directly from the exchange or place a buy order.
- **Sell Cryptocurrency**: Place a sell order for your cryptocurrency holdings.
- **View Wallet Balance**: Check your current fiat and cryptocurrency balances.
- **View Transaction History**: Review all your past transactions.

## Project Structure

- **controller**: Contains classes that handle the logic and interactions between the view and model.
- **model**: Defines the core data structures, including users and cryptocurrencies.
- **service**: Implements the business logic, such as trading operations.
- **view**: Manages user interaction and presentation.
- **exceptions**: Custom exceptions used in the application.
- **uml**: UML diagrams that document the project structure.

## JavaDocs

This project uses JavaDocs to document the code. JavaDocs provide detailed descriptions of some especial classes and methods.

### Viewing JavaDocs in IntelliJ IDEA
1. **Generate JavaDocs**:
   - You can generate JavaDocs by selecting `Tools > Generate JavaDoc...` in IntelliJ IDEA. Follow the prompts to create the documentation.
2. **View Real Time JavaDocs in IntelliJ IDEA**:
   - Go to `File>Settings>Editor>Code Completion` and check `Show the documentation popup in 500 ms`.
     
## Future Work

Out-of-scope implementations will be carried out in a forked repository. Future features include:
- **API Integration**: Fetch real-time cryptocurrency prices from a live API.
- **Telegram Chatbot Integration**: Adapt the view for use with a Telegram chatbot.
- **Advanced Price Fluctuation Features**: Implement more sophisticated logic algorithm for price fluctuations.
- **Advanced Selling Features**: Allow users to specify a maximum quantity to sell and set a price per unit of currency.

## Acknowledgments

Thanks to Globant bootcamp, I've learned OOP and Java for first time, and it has strengthened my passion for backend development. 
Before, I explored Python for AI and Solidity for blockchain, but through this experience, I've found a surprising preference for Java even more than Python, which I've used for four years.

