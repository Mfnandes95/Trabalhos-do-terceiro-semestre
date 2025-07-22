import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContaBancaria {
    private String nomeTitular;
    private int saldo;
    private List<String> extrato;
    
    public ContaBancaria(String nomeTitular, int saldoInicial) {
        this.nomeTitular = nomeTitular;
        this.saldo = saldoInicial;
        this.extrato = new ArrayList<>();
        registroTransacao("Abertura de conta", saldoInicial);
    }
    
    private void registroTransacao(String descricao, int valor) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String dataHora = LocalDateTime.now().format(formatter);
       String transacao = String.format("%s: R$ %d | Saldo: R$ %d", descricao, valor, saldo);
       extrato.add(transacao); 
       }
    
    public void depositar(int valor) {
        if(valor > 0) {
            saldo += valor;
            System.out.println("Depósito de R$" + valor + " realizado. Novo Saldo: R$" + saldo);
            registroTransacao("Depósito", valor);
        } else {
            System.out.println("Valor inválido para depósito.");
        }
    }

    public void sacar(int valor) {
        if(valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de R$" + valor + " realizado. Novo Saldo: R$" + saldo);
            registroTransacao("Saque", -valor);
        } else {
            System.out.println("Valor insuficiente.");
        }
    }
    
    public void exibirDados() {
        System.out.println("\nDados da Conta:");
        System.out.println("Titular da conta: " + nomeTitular);
        System.out.println("Saldo da Conta: R$" + saldo);
    }
    
    public void exibirExtrato() {
     System.out.println("\n=== EXTRATO BANCÁRIO ===");
     System.out.println("Titular da conta: " + nomeTitular);
     System.out.println("------------------------------------");
     
     for (String transacao : extrato) {
         System.out.println(transacao);
     }
     
     System.out.println("------------------------------------");
     System.out.println("Saldo Atual: R$ " + saldo);
     System.out.println("=============================");
     
  }
  
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Banco Digital ===");
        
        System.out.println("Insira seu nome completo: ");
        String nome = scanner.nextLine().trim();
        while(nome.isEmpty()) {
            System.out.println("Nome inválido, tente novamente");
            nome = scanner.nextLine().trim();
        }
        
        System.out.println("Insira o saldo (R$)");
        int saldoInicial;
        while(true) {
            try {
                saldoInicial = Integer.parseInt(scanner.nextLine());
                if(saldoInicial >= 0) break;
                else
                    System.out.println("Saldo Negativo");
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido");
            }
        }
        
        ContaBancaria conta = new ContaBancaria(nome, saldoInicial);
        
        int opcao;
        do {
            System.out.println("\nEscolha uma operação: ");
            System.out.println("1- Depositar");
            System.out.println("2- Sacar");
            System.out.println("3- Ver Saldo");
            System.out.println("4- Extrato");
            System.out.println("5- Sair");
         
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }
            
            switch (opcao) {
                case 1:
                    System.out.println("Valor a depositar (R$): ");
                    try {
                        int valorDeposito = Integer.parseInt(scanner.nextLine());
                        conta.depositar(valorDeposito);
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido");
                    }
                    break;
                case 2: 
                    System.out.println("Valor a sacar (R$): ");
                    try {
                        int valorSaque = Integer.parseInt(scanner.nextLine());
                        conta.sacar(valorSaque);
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido!");
                    }
                    break;
                case 3: 
                    conta.exibirDados();
                    break;
                    
                case 4: 
                    conta.exibirExtrato();
                    break;
                case 5:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (opcao != 5);
          
        scanner.close();
    }
}
