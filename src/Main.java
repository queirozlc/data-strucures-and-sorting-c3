import binary_tree.BinaryTree;
import shared.*;
import sorting.SortedLinkedList;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // In package shared.resources, there are some files
        // we have a CPF.txt file with 400 CPFs and 5 other files that simulates bank accounts
        // these files have 500, 100, 5000, 10000 and 50000 bank accounts
        // the files are named as:
        // conta500.txt, conta100.txt, conta5000.txt, conta10000.txt and conta50000.txt

        // The files are in the following format:
        // agency account balance ownerCpf
        // 1234 123456 1000 12345678900

        // The CPFs are in the following format:
        // 12345678900
        // 12345678901 ...

        // the main purpose of this program is to read the CPFs and the bank accounts
        // and sort them using different data structures that are implemented in this project
        // such as AVL tree, B-tree, Linked List and Linked Hash Table

        // The output must be a group of files with the sorted CPFs and bank accounts. Example:

        // CPF: 12345678900
        // agência: 1234 conta: 123456 saldo: 1000
        // agência: 1234 conta: 123457 saldo: 1000
        // agência: 1234 conta: 123458 saldo: 1000
        // Saldo total: 3000

        // If the cpf is not found in the bank accounts, the output must be:
        // CPF: 12345678900
        // INEXISTENTE

        // The output files must be named as:
        // cpf_avl.txt, cpf_btree.txt, cpf_linked-list.txt and cpf_linked-hash-table.txt
        // with the respective number of bank accounts, example:
        // cpf_avl_500.txt, cpf_btree_100.txt, cpf_linked-list_5000.txt and cpf_linked-hash-table_10000.txt

        var cpfBinaryTreeHandler = new CpfHandler(new BinaryTree<>());
        var cpfLinkedListHandler = new CpfHandler(new SortedLinkedList<>());

        FileProcessor.processFile("CPF.txt", cpfBinaryTreeHandler, cpfLinkedListHandler);

        var bankAccount500BinaryTreeHandler = new BankAccount500Handler(new BinaryTree<>());

        var bankAccount500LinkedListHandler = new BankAccount500Handler(new SortedLinkedList<>());

        FileProcessor.processFile("conta500.txt", bankAccount500BinaryTreeHandler, bankAccount500LinkedListHandler);

        groupByCpfTree((Tree<Long>) cpfBinaryTreeHandler.getStructure(), (Tree<BankAccount>) bankAccount500BinaryTreeHandler.getStructure());

        groupByCpfLinkedList((SortedLinkedList<Long>) cpfLinkedListHandler.getStructure(), (SortedLinkedList<BankAccount>) bankAccount500LinkedListHandler.getStructure(), SortedLinkedList::quickSort, s -> FileOutputUtils.write("cpf_linked-list_quicksort500.txt", s, "============ CPF Linked List Using Quick Sort ============\n\n"));
    }

    private static void groupByCpfLinkedList(SortedLinkedList<Long> keys, SortedLinkedList<BankAccount> values, Consumer<SortedLinkedList<?>> sortFunction, Consumer<String> writeFunction) {
        sortFunction.accept(keys);
        sortFunction.accept(values);
        var sb = new StringBuilder();
        keys.stream().forEach(key -> {
            sb.append("CPF: ").append(key).append("\n");
            AtomicReference<Double> totalBalance = new AtomicReference<>(0.0);
            AtomicBoolean found = new AtomicBoolean(false);
            values.stream().forEach(value -> {
                if (key.compareTo(value.ownerCpf()) == 0) {
                    sb.append("agência: ").append(value.agency()).append(" conta: ").append(value.account()).append(" saldo: ").append(value.balance()).append("\n");
                    totalBalance.updateAndGet(v -> v + value.balance());
                    found.set(true);
                }
            });
            if (!found.get()) {
                sb.append("INEXISTENTE\n");
            }
            if (found.get()) {
                sb.append("Saldo total: ").append(totalBalance.get()).append("\n\n");
            }

            sb.append("\n");
        });

        writeFunction.accept(sb.toString());
    }

    public static void groupByCpfTree(Tree<Long> cpfTree, Tree<BankAccount> bankAccountTree) {
        var sb = new StringBuilder();
        cpfTree.inOrderTraversal(key -> {
            sb.append("CPF: ").append(key).append("\n");
            AtomicReference<Double> totalBalance = new AtomicReference<>(0.0);
            AtomicBoolean found = new AtomicBoolean(false);
            bankAccountTree.inOrderTraversal(value -> {
                if (key.compareTo(value.ownerCpf()) == 0) {
                    sb.append("agência: ").append(value.agency()).append(" conta: ").append(value.account()).append(" saldo: ").append(value.balance()).append("\n");
                    totalBalance.updateAndGet(v -> v + value.balance());
                    found.set(true);
                }
            });
            if (!found.get()) {
                sb.append("INEXISTENTE\n");
            }
            if (found.get()) {
                sb.append("Saldo total: ").append(totalBalance.get()).append("\n\n");
            }

            sb.append("\n");
        });
        FileOutputUtils.write("cpf_btree_500.txt", sb.toString(), "============ CPF Binary Tree ============\n");
    }
}

class FileOutputUtils {

    static void write(String fileName, String content, String title) {
        var userDir = System.getProperty("user.dir");
        var separator = System.getProperty("file.separator");

        try (var writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(title);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
