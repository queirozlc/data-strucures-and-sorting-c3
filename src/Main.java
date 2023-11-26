import avl_tree.AvlTree;
import shared.*;
import sorting.SortedLinkedList;

import java.io.FileNotFoundException;

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

        var title = "============ ARVORE AVL ============\n\n";
        var cpfHandler = new CpfHandler(new AvlTree<>());
        var bankAccount500AvlTreeHandler = new BankAccountHandler(new AvlTree<>(), AccountFileOptions.CONTA_500);

        var bankAccount1000AvlTreeHandler = new BankAccountHandler(new AvlTree<>(), AccountFileOptions.CONTA_1000);

        var bankAccount5000AvlTreeHandler = new BankAccountHandler(new AvlTree<>(), AccountFileOptions.CONTA_5000);
        var bankAccount10000AvlTreeHandler = new BankAccountHandler(new AvlTree<>(), AccountFileOptions.CONTA_10000);
        var bankAccount50000AvlTreeHandler = new BankAccountHandler(new AvlTree<>(), AccountFileOptions.CONTA_50000);

        FileProcessor.processFile(cpfHandler, bankAccount500AvlTreeHandler, bankAccount1000AvlTreeHandler, bankAccount5000AvlTreeHandler, bankAccount10000AvlTreeHandler, bankAccount50000AvlTreeHandler);


        var cpfAvlTree = (AvlTree<Long>) cpfHandler.dataStructure();
        var bankAccount500AvlTree = (AvlTree<BankAccount>) bankAccount500AvlTreeHandler.dataStructure();
        var bankAccount1000AvlTree = (AvlTree<BankAccount>) bankAccount1000AvlTreeHandler.dataStructure();
        var bankAccount5000AvlTree = (AvlTree<BankAccount>) bankAccount5000AvlTreeHandler.dataStructure();
        var bankAccount10000AvlTree = (AvlTree<BankAccount>) bankAccount10000AvlTreeHandler.dataStructure();
        var bankAccount50000AvlTree = (AvlTree<BankAccount>) bankAccount50000AvlTreeHandler.dataStructure();


        new GroupProcessor(bankAccount500AvlTree)
                .process(cpfAvlTree::forEach, bankAccount500AvlTree::forEach, title, "resultado_avl500.txt");

        new GroupProcessor(bankAccount1000AvlTree)
                .process(cpfAvlTree::forEach, bankAccount1000AvlTree::forEach, title, "resultado_avl1000.txt");

        new GroupProcessor(bankAccount5000AvlTree)
                .process(cpfAvlTree::forEach, bankAccount5000AvlTree::forEach, title, "resultado_avl5000.txt");

        new GroupProcessor(bankAccount10000AvlTree)
                .process(cpfAvlTree::forEach, bankAccount10000AvlTree::forEach, title, "resultado_avl10000.txt");

        new GroupProcessor(bankAccount50000AvlTree)
                .process(cpfAvlTree::forEach, bankAccount50000AvlTree::forEach, title, "resultado_avl50000.txt");

        cpfAvlTree.clear();

        var cpfLinkedListHandler = new CpfHandler(new SortedLinkedList<>());
        var bankAccountLinkedListHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_50000);

        FileProcessor.processFile(cpfLinkedListHandler, bankAccountLinkedListHandler);

        var cpfLinkedList = (SortedLinkedList<Long>) cpfLinkedListHandler.dataStructure();
        var bankAccountLinkedList = (SortedLinkedList<BankAccount>) bankAccountLinkedListHandler.dataStructure();

        title = "============ LISTA ENCADEADA USANDO QUICK SORT ============\n\n";

        cpfLinkedList.quickSortAsync();
        bankAccountLinkedList.quickSortAsync();

        new GroupProcessor(bankAccountLinkedList)
                .process(cpfLinkedList::forEach, bankAccountLinkedList::forEach, title, "resultado_linked-list.txt");
    }
}

