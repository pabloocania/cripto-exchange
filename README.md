##### The idea of this project was to create a REST API to interact with two smart contracts:
* An ETH tradable token "QTKN"  
* An exchange that can perform this operation

The project is split in:
* The Ktor App: under ./apps/exchange-api. This is the runnable project and will load the blockchain client
and the services/routes of the api, among others

* The components.
1) `blockchain-client`: interface that specifies the methods to interact with the blockchain and contracts.
As there can be many blockchains to interact with, this project has focus on Ethereum
2) `blockchain-provider`: the web3 client implementation
3) `exchange-api-core`: the api routes
4) `exchange-api-core`: the api entities


To run the project:
`./gradlew exchange-api:run`
