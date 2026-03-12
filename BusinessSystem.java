import java.util.*;

/* ================= CUSTOMER ================= */

class Customer {
    int id;
    String name;
    double totalPurchase;

    Customer(int id, String name, double totalPurchase) {
        this.id = id;
        this.name = name;
        this.totalPurchase = totalPurchase;
    }
}

/* ================= LINKED LIST (CO2) ================= */

class Order {
    int orderId;
    String item;
    Order next;

    Order(int id, String item) {
        orderId = id;
        this.item = item;
    }
}

class OrderList {
    Order head;

    void addOrder(int id, String item) {
        Order newOrder = new Order(id, item);
        newOrder.next = head;
        head = newOrder;
    }

    void displayOrders() {
        Order temp = head;

        while (temp != null) {
            System.out.println("OrderID: " + temp.orderId + " Item: " + temp.item);
            temp = temp.next;
        }
    }
}

/* ================= STACK BALANCE CHECK (CO3) ================= */

class DiscountChecker {

    static boolean isBalanced(String exp) {

        Stack<Character> stack = new Stack<>();

        for (char c : exp.toCharArray()) {

            if (c == '(' || c == '{' || c == '[')
                stack.push(c);

            else if (c == ')' || c == '}' || c == ']') {

                if (stack.isEmpty())
                    return false;

                char top = stack.pop();

                if ((c == ')' && top != '(') ||
                        (c == '}' && top != '{') ||
                        (c == ']' && top != '['))
                    return false;
            }
        }

        return stack.isEmpty();
    }
}

/* ================= CIRCULAR QUEUE (CO3) ================= */

class CircularQueue {

    int[] queue;
    int front = -1, rear = -1, size;

    CircularQueue(int size) {
        this.size = size;
        queue = new int[size];
    }

    void enqueue(int value) {

        if ((rear + 1) % size == front) {
            System.out.println("Queue Full");
            return;
        }

        if (front == -1)
            front = 0;

        rear = (rear + 1) % size;
        queue[rear] = value;
    }

    void dequeue() {

        if (front == -1) {
            System.out.println("Queue Empty");
            return;
        }

        System.out.println("Served Customer ID: " + queue[front]);

        if (front == rear)
            front = rear = -1;
        else
            front = (front + 1) % size;
    }
}

/* ================= HASH TABLE (CO4) ================= */

class HashTable {

    int size = 10;
    int table[] = new int[size];

    HashTable() {
        Arrays.fill(table, -1);
    }

    int hash(int key) {
        return key % size;
    }

    void insert(int key) {

        int index = hash(key);

        while (table[index] != -1)
            index = (index + 1) % size;

        table[index] = key;
    }

    void display() {

        for (int i = 0; i < size; i++)
            System.out.println(i + " -> " + table[i]);
    }
}

/* ================= MAIN SYSTEM ================= */

public class BusinessSystem {

    static Scanner sc = new Scanner(System.in);

    static List<Customer> customers = new ArrayList<>();
    static Queue<Customer> serviceQueue = new LinkedList<>();
    static HashMap<Integer, Customer> customerMap = new HashMap<>();

    static PriorityQueue<Customer> highValueCustomers = new PriorityQueue<>(
            (a, b) -> Double.compare(b.totalPurchase, a.totalPurchase));

    static OrderList orderList = new OrderList();

    /* ================= CO1 SEARCHING ================= */

    static int linearSearch(int arr[], int key) {

        for (int i = 0; i < arr.length; i++)
            if (arr[i] == key)
                return i;

        return -1;
    }

    static int binarySearch(int arr[], int key) {

        int low = 0, high = arr.length - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (arr[mid] == key)
                return mid;

            if (arr[mid] < key)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return -1;
    }

    /* ================= CO1 SORTING ================= */

    static void bubbleSort(int arr[]) {

        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - i - 1; j++)
                if (arr[j] > arr[j + 1]) {

                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
    }

    static void selectionSort(int arr[]) {

        for (int i = 0; i < arr.length - 1; i++) {

            int min = i;

            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[min])
                    min = j;

            int temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }

    static void insertionSort(int arr[]) {

        for (int i = 1; i < arr.length; i++) {

            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {

                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    /* ================= MERGE SORT ================= */

    static void merge(int arr[], int l, int m, int r) {

        int n1 = m - l + 1;
        int n2 = r - m;

        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[l + i];

        for (int j = 0; j < n2; j++)
            R[j] = arr[m + 1 + j];

        int i = 0, j = 0, k = l;

        while (i < n1 && j < n2)
            arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];

        while (i < n1)
            arr[k++] = L[i++];

        while (j < n2)
            arr[k++] = R[j++];
    }

    static void mergeSort(int arr[], int l, int r) {

        if (l < r) {

            int m = (l + r) / 2;

            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    /* ================= QUICK SORT ================= */

    static int partition(int arr[], int low, int high) {

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++)
            if (arr[j] < pivot) {

                i++;

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    static void quickSort(int arr[], int low, int high) {

        if (low < high) {

            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    /* ================= BUSINESS FUNCTIONS ================= */

    static void addCustomer() {

        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        System.out.print("Enter Name: ");
        String name = sc.next();

        System.out.print("Enter Purchase: ");
        double purchase = sc.nextDouble();

        Customer c = new Customer(id, name, purchase);

        customers.add(c);
        customerMap.put(id, c);
        highValueCustomers.add(c);

        System.out.println("Customer Added");
    }

    static void searchCustomer() {

        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        if (customerMap.containsKey(id)) {

            Customer c = customerMap.get(id);

            System.out.println("Found: " + c.name + " Purchase: " + c.totalPurchase);

        } else
            System.out.println("Customer Not Found");
    }

    static void addOrder() {

        System.out.print("Order ID: ");
        int id = sc.nextInt();

        System.out.print("Item: ");
        String item = sc.next();

        orderList.addOrder(id, item);

        System.out.println("Order Added");
    }

    static void showHighValueCustomer() {

        Customer c = highValueCustomers.peek();

        if (c != null)
            System.out.println("Top Customer: " + c.name + " Purchase: " + c.totalPurchase);
    }

    /* ================= MAIN MENU ================= */

    public static void main(String[] args) {

        CircularQueue cq = new CircularQueue(5);
        HashTable ht = new HashTable();

        while (true) {

            System.out.println("\n===== Business Sales Management System =====");

            System.out.println("1 Add Customer");
            System.out.println("2 Search Customer (HashMap)");
            System.out.println("3 Add Order (Linked List)");
            System.out.println("4 Display Orders");
            System.out.println("5 Add Customer to Queue");
            System.out.println("6 Serve Customer");
            System.out.println("7 Circular Queue Demo");
            System.out.println("8 Check Discount Expression (Stack)");
            System.out.println("9 Show High Value Customer (Heap)");
            System.out.println("10 Sorting Demo");
            System.out.println("11 Hash Table Demo");
            System.out.println("0 Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addCustomer();
                    break;

                case 2:
                    searchCustomer();
                    break;

                case 3:
                    addOrder();
                    break;

                case 4:
                    orderList.displayOrders();
                    break;

                case 5:
                    System.out.print("Enter Customer ID: ");
                    int id = sc.nextInt();

                    if (customerMap.containsKey(id))
                        serviceQueue.add(customerMap.get(id));
                    break;

                case 6:
                    if (!serviceQueue.isEmpty())
                        System.out.println("Serving " + serviceQueue.poll().name);
                    break;

                case 7:
                    cq.enqueue(101);
                    cq.enqueue(102);
                    cq.dequeue();
                    break;

                case 8:
                    System.out.print("Expression: ");
                    String exp = sc.next();

                    System.out.println(DiscountChecker.isBalanced(exp) ? "Valid Expression" : "Invalid Expression");
                    break;

                case 9:
                    showHighValueCustomer();
                    break;

                case 10:
                    int arr[] = { 5, 2, 9, 1, 3 };
                    bubbleSort(arr);

                    System.out.println("Sorted Array:");
                    for (int i : arr)
                        System.out.print(i + " ");
                    break;

                case 11:
                    ht.insert(25);
                    ht.insert(35);
                    ht.insert(45);
                    ht.display();
                    break;

                case 0:
                    System.exit(0);
            }
        }
    }
}