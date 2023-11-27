import avl_tree.AvlTree;
import binary_tree.BinaryTree;
import hash.LinkedHashTable;
import shared.*;
import sorting.SortedLinkedList;

import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;

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

        // PROCESS THE SHELL SORTS FIRST ASYNCHRONOUSLY
        var cpfShellSortHandler = new CpfHandler(new SortedLinkedList<>());
        var bankAccount500ShellSortHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_500);
        var bankAccount1000ShellSortHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_1000);
        var bankAccount5000ShellSortHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_5000);
        var bankAccount10000ShellSortHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_10000);
        var bankAccount50000ShellSortHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_50000);

        FileProcessor.processFile(cpfShellSortHandler, bankAccount500ShellSortHandler, bankAccount1000ShellSortHandler, bankAccount5000ShellSortHandler, bankAccount10000ShellSortHandler, bankAccount50000ShellSortHandler);

        var cpfShellSortLinkedList = (SortedLinkedList<Cpf>) cpfShellSortHandler.dataStructure();
        var bankAccount500ShellSortLinkedList = (SortedLinkedList<BankAccount>) bankAccount500ShellSortHandler.dataStructure();
        var bankAccount1000ShellSortLinkedList = (SortedLinkedList<BankAccount>) bankAccount1000ShellSortHandler.dataStructure();
        var bankAccount5000ShellSortLinkedList = (SortedLinkedList<BankAccount>) bankAccount5000ShellSortHandler.dataStructure();
        var bankAccount10000ShellSortLinkedList = (SortedLinkedList<BankAccount>) bankAccount10000ShellSortHandler.dataStructure();
        var bankAccount50000ShellSortLinkedList = (SortedLinkedList<BankAccount>) bankAccount50000ShellSortHandler.dataStructure();

        // use the completable future to process the shell sorts asynchronously
        CompletableFuture.allOf(
                CompletableFuture.runAsync(cpfShellSortLinkedList::shellSort),
                CompletableFuture.runAsync(bankAccount500ShellSortLinkedList::shellSort),
                CompletableFuture.runAsync(bankAccount1000ShellSortLinkedList::shellSort),
                CompletableFuture.runAsync(bankAccount5000ShellSortLinkedList::shellSort),
                CompletableFuture.runAsync(bankAccount10000ShellSortLinkedList::shellSort),
                CompletableFuture.runAsync(bankAccount50000ShellSortLinkedList::shellSort)
        );

        var title = "============ ARVORE AVL ============\n\n";
        var cpfHandler = new CpfHandler(new AvlTree<>());
        var bankAccount500AvlTreeHandler = new BankAccountHandler(new AvlTree<>(), AccountFileOptions.CONTA_500);

        var bankAccount1000AvlTreeHandler = new BankAccountHandler(new AvlTree<>(), AccountFileOptions.CONTA_1000);

        var bankAccount5000AvlTreeHandler = new BankAccountHandler(new AvlTree<>(), AccountFileOptions.CONTA_5000);
        var bankAccount10000AvlTreeHandler = new BankAccountHandler(new AvlTree<>(), AccountFileOptions.CONTA_10000);
        var bankAccount50000AvlTreeHandler = new BankAccountHandler(new AvlTree<>(), AccountFileOptions.CONTA_50000);

        FileProcessor.processFile(cpfHandler, bankAccount500AvlTreeHandler, bankAccount1000AvlTreeHandler, bankAccount5000AvlTreeHandler, bankAccount10000AvlTreeHandler, bankAccount50000AvlTreeHandler);


        var cpfAvlTree = (AvlTree<Cpf>) cpfHandler.dataStructure();
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

        title = "============ ÁRVORE BINÁRIA DE BUSCA ============\n\n";
        var cpfBinaryTreeHandler = new CpfHandler(new BinaryTree<>());
        var bankAccount500BinaryTreeHandler = new BankAccountHandler(new BinaryTree<>(), AccountFileOptions.CONTA_500);
        var bankAccount1000BinaryTreeHandler = new BankAccountHandler(new BinaryTree<>(), AccountFileOptions.CONTA_1000);
        var bankAccount5000BinaryTreeHandler = new BankAccountHandler(new BinaryTree<>(), AccountFileOptions.CONTA_5000);
        var bankAccount10000BinaryTreeHandler = new BankAccountHandler(new BinaryTree<>(), AccountFileOptions.CONTA_10000);
        var bankAccount50000BinaryTreeHandler = new BankAccountHandler(new BinaryTree<>(), AccountFileOptions.CONTA_50000);

        FileProcessor.processFile(cpfBinaryTreeHandler, bankAccount500BinaryTreeHandler, bankAccount1000BinaryTreeHandler, bankAccount5000BinaryTreeHandler, bankAccount10000BinaryTreeHandler, bankAccount50000BinaryTreeHandler);

        var cpfBinaryTree = (BinaryTree<Cpf>) cpfBinaryTreeHandler.dataStructure();

        var bankAccount500BinaryTree = (BinaryTree<BankAccount>) bankAccount500BinaryTreeHandler.dataStructure();

        var bankAccount1000BinaryTree = (BinaryTree<BankAccount>) bankAccount1000BinaryTreeHandler.dataStructure();

        var bankAccount5000BinaryTree = (BinaryTree<BankAccount>) bankAccount5000BinaryTreeHandler.dataStructure();

        var bankAccount10000BinaryTree = (BinaryTree<BankAccount>) bankAccount10000BinaryTreeHandler.dataStructure();

        var bankAccount50000BinaryTree = (BinaryTree<BankAccount>) bankAccount50000BinaryTreeHandler.dataStructure();

        new GroupProcessor(bankAccount500BinaryTree)
                .process(cpfBinaryTree::forEach, bankAccount500BinaryTree::forEach, title, "resultado_binary-tree500.txt");

        new GroupProcessor(bankAccount1000BinaryTree)
                .process(cpfBinaryTree::forEach, bankAccount1000BinaryTree::forEach, title, "resultado_binary-tree1000.txt");

        new GroupProcessor(bankAccount5000BinaryTree)
                .process(cpfBinaryTree::forEach, bankAccount5000BinaryTree::forEach, title, "resultado_binary-tree5000.txt");

        new GroupProcessor(bankAccount10000BinaryTree)
                .process(cpfBinaryTree::forEach, bankAccount10000BinaryTree::forEach, title, "resultado_binary-tree10000.txt");

        new GroupProcessor(bankAccount50000BinaryTree)
                .process(cpfBinaryTree::forEach, bankAccount50000BinaryTree::forEach, title, "resultado_binary-tree50000.txt");

        cpfBinaryTree.clear();

        title = "============ TABELA HASH VETOR ENCADEADO ============\n\n";
        var cpfLinkedHashTableHandler = new CpfHandler(new LinkedHashTable<>());
        var bankAccount500LinkedHashTableHandler = new BankAccountHandler(new LinkedHashTable<>(), AccountFileOptions.CONTA_500);
        var bankAccount1000LinkedHashTableHandler = new BankAccountHandler(new LinkedHashTable<>(), AccountFileOptions.CONTA_1000);
        var bankAccount5000LinkedHashTableHandler = new BankAccountHandler(new LinkedHashTable<>(), AccountFileOptions.CONTA_5000);
        var bankAccount10000LinkedHashTableHandler = new BankAccountHandler(new LinkedHashTable<>(), AccountFileOptions.CONTA_10000);
        var bankAccount50000LinkedHashTableHandler = new BankAccountHandler(new LinkedHashTable<>(), AccountFileOptions.CONTA_50000);

        FileProcessor.processFile(cpfLinkedHashTableHandler, bankAccount500LinkedHashTableHandler, bankAccount1000LinkedHashTableHandler, bankAccount5000LinkedHashTableHandler, bankAccount10000LinkedHashTableHandler, bankAccount50000LinkedHashTableHandler);

        var cpfLinkedHashTable = (LinkedHashTable<Cpf>) cpfLinkedHashTableHandler.dataStructure();
        var bankAccount500LinkedHashTable = (LinkedHashTable<BankAccount>) bankAccount500LinkedHashTableHandler.dataStructure();
        var bankAccount1000LinkedHashTable = (LinkedHashTable<BankAccount>) bankAccount1000LinkedHashTableHandler.dataStructure();
        var bankAccount5000LinkedHashTable = (LinkedHashTable<BankAccount>) bankAccount5000LinkedHashTableHandler.dataStructure();
        var bankAccount10000LinkedHashTable = (LinkedHashTable<BankAccount>) bankAccount10000LinkedHashTableHandler.dataStructure();
        var bankAccount50000LinkedHashTable = (LinkedHashTable<BankAccount>) bankAccount50000LinkedHashTableHandler.dataStructure();

        new GroupProcessor(bankAccount500LinkedHashTable)
                .process(cpfLinkedHashTable::forEach, bankAccount500LinkedHashTable::forEach, title, "resultado_linked-hash-table500.txt");

        new GroupProcessor(bankAccount1000LinkedHashTable)
                .process(cpfLinkedHashTable::forEach, bankAccount1000LinkedHashTable::forEach, title, "resultado_linked-hash-table1000.txt");

        new GroupProcessor(bankAccount5000LinkedHashTable)
                .process(cpfLinkedHashTable::forEach, bankAccount5000LinkedHashTable::forEach, title, "resultado_linked-hash-table5000.txt");

        new GroupProcessor(bankAccount10000LinkedHashTable)
                .process(cpfLinkedHashTable::forEach, bankAccount10000LinkedHashTable::forEach, title, "resultado_linked-hash-table10000.txt");

        new GroupProcessor(bankAccount50000LinkedHashTable)
                .process(cpfLinkedHashTable::forEach, bankAccount50000LinkedHashTable::forEach, title, "resultado_linked-hash-table50000.txt");

        cpfLinkedHashTable.clear();

        title = "============ LISTA ENCADEADA USANDO QUICK SORT ============\n\n";
        var cpfQuickSortHandler = new CpfHandler(new SortedLinkedList<>());
        var bankAccount500QuickSortHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_500);
        var bankAccount1000QuickSortHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_1000);
        var bankAccount5000QuickSortHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_5000);
        var bankAccount10000QuickSortHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_10000);
        var bankAccount50000QuickSortHandler = new BankAccountHandler(new SortedLinkedList<>(), AccountFileOptions.CONTA_50000);

        FileProcessor.processFile(cpfQuickSortHandler, bankAccount500QuickSortHandler, bankAccount1000QuickSortHandler, bankAccount5000QuickSortHandler, bankAccount10000QuickSortHandler, bankAccount50000QuickSortHandler);

        var cpfLinkedList = (SortedLinkedList<Cpf>) cpfQuickSortHandler.dataStructure();
        var bankAccount500LinkedList = (SortedLinkedList<BankAccount>) bankAccount500QuickSortHandler.dataStructure();
        var bankAccount1000LinkedList = (SortedLinkedList<BankAccount>) bankAccount1000QuickSortHandler.dataStructure();
        var bankAccount5000LinkedList = (SortedLinkedList<BankAccount>) bankAccount5000QuickSortHandler.dataStructure();
        var bankAccount10000LinkedList = (SortedLinkedList<BankAccount>) bankAccount10000QuickSortHandler.dataStructure();
        var bankAccount50000LinkedList = (SortedLinkedList<BankAccount>) bankAccount50000QuickSortHandler.dataStructure();


        cpfLinkedList.quickSortAsync();
        bankAccount500LinkedList.quickSortAsync();
        bankAccount1000LinkedList.quickSortAsync();
        bankAccount5000LinkedList.quickSortAsync();
        bankAccount10000LinkedList.quickSortAsync();
        bankAccount50000LinkedList.quickSortAsync();


        new GroupProcessor(bankAccount500LinkedList)
                .process(cpfLinkedList::forEach, bankAccount500LinkedList::forEach, title, "resultado_quicksort500.txt");

        new GroupProcessor(bankAccount1000LinkedList)
                .process(cpfLinkedList::forEach, bankAccount1000LinkedList::forEach, title, "resultado_quicksort1000.txt");

        new GroupProcessor(bankAccount5000LinkedList)
                .process(cpfLinkedList::forEach, bankAccount5000LinkedList::forEach, title, "resultado_quicksort5000.txt");

        new GroupProcessor(bankAccount10000LinkedList)
                .process(cpfLinkedList::forEach, bankAccount10000LinkedList::forEach, title, "resultado_quicksort10000.txt");

        new GroupProcessor(bankAccount50000LinkedList)
                .process(cpfLinkedList::forEach, bankAccount50000LinkedList::forEach, title, "resultado_quicksort50000.txt");
        System.out.println(cpfLinkedList.size());
        cpfLinkedList.clear();

        title = "============ LISTA ENCADEADA USANDO SHELL SORT ============\n\n";


        new GroupProcessor(bankAccount500ShellSortLinkedList)
                .process(cpfShellSortLinkedList::forEach, bankAccount500ShellSortLinkedList::forEach, title, "resultado_shellsort500.txt");

        new GroupProcessor(bankAccount1000ShellSortLinkedList)
                .process(cpfShellSortLinkedList::forEach, bankAccount1000ShellSortLinkedList::forEach, title, "resultado_shellsort1000.txt");

        new GroupProcessor(bankAccount5000ShellSortLinkedList)
                .process(cpfShellSortLinkedList::forEach, bankAccount5000ShellSortLinkedList::forEach, title, "resultado_shellsort5000.txt");

        new GroupProcessor(bankAccount10000ShellSortLinkedList)
                .process(cpfShellSortLinkedList::forEach, bankAccount10000ShellSortLinkedList::forEach, title, "resultado_shellsort10000.txt");

        new GroupProcessor(bankAccount50000ShellSortLinkedList)
                .process(cpfShellSortLinkedList::forEach, bankAccount50000ShellSortLinkedList::forEach, title, "resultado_shellsort50000.txt");

        cpfShellSortLinkedList.clear();
    }
}