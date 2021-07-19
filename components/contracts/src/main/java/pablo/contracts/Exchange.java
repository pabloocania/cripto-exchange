package pablo.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.8.3.
 */
@SuppressWarnings("rawtypes")
public class Exchange extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516111a73803806111a78339818101604052604081101561003357600080fd5b50805160209091015160008054600160a060020a031916600160a060020a0390931692909217825560015561113990819061006e90396000f3fe608060405260043610610105576000357c01000000000000000000000000000000000000000000000000000000009004806367b830ad116100a75780639e281a98116100765780639e281a98146102f8578063a85c38ef14610324578063c72abc5014610396578063f7888aec146103c057610105565b806367b830ad1461026a5780637fd6f15c1461029457806398ea5fca146102a95780639a5c90aa146102b157610105565b80633bed33ce116100e35780633bed33ce146101b7578063508493bc146101d4578063514fcac71461020f57806365e17c9d1461023957610105565b806321168620146101175780632453ffa814610155578063338b5dea1461017c575b34801561011157600080fd5b50600080fd5b34801561012357600080fd5b506101416004803603602081101561013a57600080fd5b50356103fb565b604080519115158252519081900360200190f35b34801561016157600080fd5b5061016a610410565b60408051918252519081900360200190f35b34801561018857600080fd5b506101b56004803603604081101561019f57600080fd5b50600160a060020a038135169060200135610416565b005b6101b5600480360360208110156101cd57600080fd5b503561056d565b3480156101e057600080fd5b5061016a600480360360408110156101f757600080fd5b50600160a060020a038135811691602001351661066f565b34801561021b57600080fd5b506101b56004803603602081101561023257600080fd5b503561068c565b34801561024557600080fd5b5061024e610757565b60408051600160a060020a039092168252519081900360200190f35b34801561027657600080fd5b506101b56004803603602081101561028d57600080fd5b5035610766565b3480156102a057600080fd5b5061016a610821565b6101b5610827565b3480156102bd57600080fd5b506101b5600480360360808110156102d457600080fd5b50600160a060020a03813581169160208101359160408201351690606001356108b6565b6101b56004803603604081101561030e57600080fd5b50600160a060020a038135169060200135610a6b565b34801561033057600080fd5b5061034e6004803603602081101561034757600080fd5b5035610c02565b60408051978852600160a060020a0396871660208901529486168786015260608701939093529316608085015260a084019290925260c0830191909152519081900360e00190f35b3480156103a257600080fd5b50610141600480360360208110156103b957600080fd5b5035610c4e565b3480156103cc57600080fd5b5061016a600480360360408110156103e357600080fd5b50600160a060020a0381358116916020013516610c63565b60066020526000908152604090205460ff1681565b60045481565b600160a060020a03821661042957600080fd5b604080517f23b872dd000000000000000000000000000000000000000000000000000000008152336004820152306024820152604481018390529051600160a060020a038416916323b872dd9160648083019260209291908290030181600087803b15801561049757600080fd5b505af11580156104ab573d6000803e3d6000fd5b505050506040513d60208110156104c157600080fd5b50516104cc57600080fd5b600160a060020a03821660009081526002602090815260408083203384529091529020546104fa9082610c90565b600160a060020a03831660008181526002602090815260408083203380855290835292819020859055805193845290830191909152818101849052606082019290925290517fdcbc1c05240f31ff3ad067ef1ee35ce4997762752e3a095284754544f4c709d79181900360800190a15050565b3360009081526000805160206110c3833981519152602052604090205481111561059657600080fd5b3360009081526000805160206110c383398151915260205260409020546105bd9082610cf4565b3360008181526000805160206110c38339815191526020526040808220939093559151909183156108fc02918491818181858888f19350505050158015610608573d6000803e3d6000fd5b503360008181526000805160206110c38339815191526020908152604080832054815193845291830193909352818301849052606082015290517ff341246adaac6f497bc2a656f546ab9e182111d630394f0c57c710a59a2cb5679181900360800190a150565b600260209081526000928352604080842090915290825290205481565b60008181526003602052604090206001810154600160a060020a031633146106b357600080fd5b805482146106c057600080fd5b600082815260056020818152604092839020805460ff1916600117905583546002850154600386015460048701549487015486519384523394840194909452600160a060020a0391821683870152606083015292909216608083015260a08201524260c082015290517f77de1da0be5f2d3b110d05c6694e5c4ff2d9da7cd23d84353ecf78c7b5acec619181900360e00190a15050565b600054600160a060020a031681565b60008111801561077857506004548111155b61078157600080fd5b60008181526006602052604090205460ff161561079d57600080fd5b60008181526005602052604090205460ff16156107b957600080fd5b60008181526003602081905260409091208054600182015460028301549383015460048401546005850154949561080495600160a060020a0394851694918216939290911690610d36565b546000908152600660205260409020805460ff1916600117905550565b60015481565b3360009081526000805160206110c3833981519152602052604090205461084e9034610c90565b3360008181526000805160206110c38339815191526020908152604080832085905580519283529082019290925234818301526060810192909252517fdcbc1c05240f31ff3ad067ef1ee35ce4997762752e3a095284754544f4c709d79181900360800190a1565b6004546108c4906001610c90565b6004819055506040518060e00160405280600454815260200133600160a060020a0316815260200185600160a060020a0316815260200184815260200183600160a060020a03168152602001828152602001428152506003600060045481526020019081526020016000206000820151816000015560208201518160010160006101000a815481600160a060020a030219169083600160a060020a0316021790555060408201518160020160006101000a815481600160a060020a030219169083600160a060020a031602179055506060820151816003015560808201518160040160006101000a815481600160a060020a030219169083600160a060020a0316021790555060a0820151816005015560c082015181600601559050507f9d33853d43e3607b4cc01fdc78338ff2dca6fef7db9232dae6d3eb55fbc3b1096004543386868686426040518088815260200187600160a060020a0316815260200186600160a060020a0316815260200185815260200184600160a060020a0316815260200183815260200182815260200197505050505050505060405180910390a150505050565b600160a060020a038216610a7e57600080fd5b600160a060020a0382166000908152600260209081526040808320338452909152902054811115610aae57600080fd5b600160a060020a0382166000908152600260209081526040808320338452909152902054610adc9082610cf4565b600160a060020a0383166000818152600260209081526040808320338085529083528184209590955580517fa9059cbb00000000000000000000000000000000000000000000000000000000815260048101959095526024850186905251929363a9059cbb9360448083019491928390030190829087803b158015610b6057600080fd5b505af1158015610b74573d6000803e3d6000fd5b505050506040513d6020811015610b8a57600080fd5b5051610b9557600080fd5b600160a060020a03821660008181526002602090815260408083203380855290835292819020548151948552918401929092528282018490526060830152517ff341246adaac6f497bc2a656f546ab9e182111d630394f0c57c710a59a2cb5679181900360800190a15050565b600360208190526000918252604090912080546001820154600283015493830154600484015460058501546006909501549395600160a060020a03938416959084169492939091169187565b60056020526000908152604090205460ff1681565b600160a060020a038083166000908152600260209081526040808320938516835292905220545b92915050565b600082820183811015610ced576040805160e560020a62461bcd02815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015290519081900360640190fd5b9392505050565b6000610ced83836040518060400160405280601e81526020017f536166654d6174683a207375627472616374696f6e206f766572666c6f770000815250610f27565b6000610d586064610d5260015487610fc190919063ffffffff16565b9061101d565b9050610d91610d678583610c90565b600160a060020a038716600090815260026020908152604080832033845290915290205490610cf4565b600160a060020a0386811660009081526002602090815260408083203384529091528082209390935590881681522054610dcb9085610c90565b600160a060020a0386811660009081526002602090815260408083208b8516845290915280822093909355805490911681522054610e099082610c90565b600160a060020a0380871660009081526002602081815260408084208454861685528252808420959095558784168352908152838220928a168252919091522054610e549083610cf4565b600160a060020a038481166000908152600260209081526040808320938b16835292905281812092909255338252902054610e8f9083610c90565b600160a060020a03808516600081815260026020908152604080832033808552908352928190209590955584518c81528b8516918101919091529289168385015260608301889052608083019190915260a0820185905260c08201524260e082015290517f0dddf4182dc0fc1a311cb75f049c97403c6b8a99cf6b2229a36e7c526acb3f81918190036101000190a150505050505050565b60008184841115610fb95760405160e560020a62461bcd0281526004018080602001828103825283818151815260200191508051906020019080838360005b83811015610f7e578181015183820152602001610f66565b50505050905090810190601f168015610fab5780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b505050900390565b600082610fd057506000610c8a565b82820282848281610fdd57fe5b0414610ced5760405160e560020a62461bcd0281526004018080602001828103825260218152602001806110e36021913960400191505060405180910390fd5b6000610ced83836040518060400160405280601a81526020017f536166654d6174683a206469766973696f6e206279207a65726f000000000000815250600081836110ac5760405160e560020a62461bcd028152602060048201818152835160248401528351909283926044909101919085019080838360008315610f7e578181015183820152602001610f66565b5060008385816110b857fe5b049594505050505056feac33ff75c19e70fe83507db0d683fd3465c996598dc972688b7ace676c89077b536166654d6174683a206d756c7469706c69636174696f6e206f766572666c6f77a264697066735822122087ed59280a61ba6f2ec3e5efe817e893f884eecd553a0250a32b4ca6ba2e336164736f6c634300060c0033";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_CANCELORDER = "cancelOrder";

    public static final String FUNC_DEPOSITETHER = "depositEther";

    public static final String FUNC_DEPOSITTOKEN = "depositToken";

    public static final String FUNC_FEEACCOUNT = "feeAccount";

    public static final String FUNC_FEEPERCENT = "feePercent";

    public static final String FUNC_FILLORDER = "fillOrder";

    public static final String FUNC_MAKEORDER = "makeOrder";

    public static final String FUNC_ORDERCOUNT = "orderCount";

    public static final String FUNC_ORDERS = "orders";

    public static final String FUNC_ORDERSCANCELLED = "ordersCancelled";

    public static final String FUNC_ORDERSFILLED = "ordersFilled";

    public static final String FUNC_TOKENS = "tokens";

    public static final String FUNC_WITHDRAWETHER = "withdrawEther";

    public static final String FUNC_WITHDRAWTOKEN = "withdrawToken";

    public static final Event CANCEL_EVENT = new Event("Cancel", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DEPOSIT_EVENT = new Event("Deposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ORDER_EVENT = new Event("Order", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRADE_EVENT = new Event("Trade", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAW_EVENT = new Event("Withdraw", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Exchange(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Exchange(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Exchange(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Exchange(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CancelEventResponse> getCancelEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CANCEL_EVENT, transactionReceipt);
        ArrayList<CancelEventResponse> responses = new ArrayList<CancelEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CancelEventResponse typedResponse = new CancelEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokenGet = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.tokenGive = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CancelEventResponse> cancelEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CancelEventResponse>() {
            @Override
            public CancelEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CANCEL_EVENT, log);
                CancelEventResponse typedResponse = new CancelEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.tokenGet = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.tokenGive = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CancelEventResponse> cancelEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANCEL_EVENT));
        return cancelEventFlowable(filter);
    }

    public List<DepositEventResponse> getDepositEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSIT_EVENT, transactionReceipt);
        ArrayList<DepositEventResponse> responses = new ArrayList<DepositEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositEventResponse typedResponse = new DepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.balance = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DepositEventResponse> depositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DepositEventResponse>() {
            @Override
            public DepositEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DEPOSIT_EVENT, log);
                DepositEventResponse typedResponse = new DepositEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.balance = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DepositEventResponse> depositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSIT_EVENT));
        return depositEventFlowable(filter);
    }

    public List<OrderEventResponse> getOrderEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ORDER_EVENT, transactionReceipt);
        ArrayList<OrderEventResponse> responses = new ArrayList<OrderEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderEventResponse typedResponse = new OrderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokenGet = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.tokenGive = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OrderEventResponse> orderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OrderEventResponse>() {
            @Override
            public OrderEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ORDER_EVENT, log);
                OrderEventResponse typedResponse = new OrderEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.tokenGet = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.tokenGive = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OrderEventResponse> orderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDER_EVENT));
        return orderEventFlowable(filter);
    }

    public List<TradeEventResponse> getTradeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRADE_EVENT, transactionReceipt);
        ArrayList<TradeEventResponse> responses = new ArrayList<TradeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TradeEventResponse typedResponse = new TradeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokenGet = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.tokenGive = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.userFill = (String) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TradeEventResponse> tradeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TradeEventResponse>() {
            @Override
            public TradeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRADE_EVENT, log);
                TradeEventResponse typedResponse = new TradeEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.tokenGet = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.amountGet = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.tokenGive = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.amountGive = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.userFill = (String) eventValues.getNonIndexedValues().get(6).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TradeEventResponse> tradeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRADE_EVENT));
        return tradeEventFlowable(filter);
    }

    public List<WithdrawEventResponse> getWithdrawEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAW_EVENT, transactionReceipt);
        ArrayList<WithdrawEventResponse> responses = new ArrayList<WithdrawEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawEventResponse typedResponse = new WithdrawEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.balance = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, WithdrawEventResponse>() {
            @Override
            public WithdrawEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAW_EVENT, log);
                WithdrawEventResponse typedResponse = new WithdrawEventResponse();
                typedResponse.log = log;
                typedResponse.token = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.balance = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAW_EVENT));
        return withdrawEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String _token, String _user) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Address(160, _user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelOrder(BigInteger _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCELORDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositEther(BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITETHER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> depositToken(String _token, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> feeAccount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FEEACCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> feePercent() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FEEPERCENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> fillOrder(BigInteger _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FILLORDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> makeOrder(String _tokenGet, BigInteger _amountGet, String _tokenGive, BigInteger _amountGive) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MAKEORDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _tokenGet), 
                new org.web3j.abi.datatypes.generated.Uint256(_amountGet), 
                new org.web3j.abi.datatypes.Address(160, _tokenGive), 
                new org.web3j.abi.datatypes.generated.Uint256(_amountGive)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> orderCount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ORDERCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<BigInteger, String, String, BigInteger, String, BigInteger, BigInteger>> orders(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ORDERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple7<BigInteger, String, String, BigInteger, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple7<BigInteger, String, String, BigInteger, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple7<BigInteger, String, String, BigInteger, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, String, BigInteger, String, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> ordersCancelled(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ORDERSCANCELLED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> ordersFilled(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ORDERSFILLED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> tokens(String param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawEther(BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWETHER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawToken(String _token, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Exchange load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Exchange(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Exchange load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Exchange(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Exchange load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Exchange(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Exchange load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Exchange(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Exchange> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _feeAccount, BigInteger _feePercent) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _feeAccount), 
                new org.web3j.abi.datatypes.generated.Uint256(_feePercent)));
        return deployRemoteCall(Exchange.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Exchange> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _feeAccount, BigInteger _feePercent) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _feeAccount), 
                new org.web3j.abi.datatypes.generated.Uint256(_feePercent)));
        return deployRemoteCall(Exchange.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Exchange> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _feeAccount, BigInteger _feePercent) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _feeAccount), 
                new org.web3j.abi.datatypes.generated.Uint256(_feePercent)));
        return deployRemoteCall(Exchange.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Exchange> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _feeAccount, BigInteger _feePercent) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _feeAccount), 
                new org.web3j.abi.datatypes.generated.Uint256(_feePercent)));
        return deployRemoteCall(Exchange.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class CancelEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public String tokenGet;

        public BigInteger amountGet;

        public String tokenGive;

        public BigInteger amountGive;

        public BigInteger timestamp;
    }

    public static class DepositEventResponse extends BaseEventResponse {
        public String token;

        public String user;

        public BigInteger amount;

        public BigInteger balance;
    }

    public static class OrderEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public String tokenGet;

        public BigInteger amountGet;

        public String tokenGive;

        public BigInteger amountGive;

        public BigInteger timestamp;
    }

    public static class TradeEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public String tokenGet;

        public BigInteger amountGet;

        public String tokenGive;

        public BigInteger amountGive;

        public String userFill;

        public BigInteger timestamp;
    }

    public static class WithdrawEventResponse extends BaseEventResponse {
        public String token;

        public String user;

        public BigInteger amount;

        public BigInteger balance;
    }
}
