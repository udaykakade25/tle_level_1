#include <iostream>
#include <string>
using namespace std;

class Node {
public:
    int key;
    string value;
    Node *left;
    Node *right;
    
    Node(int k, string v) {
        key = k;
        value = v;
        left = NULL;
        right = NULL;
    }
};

Node* insert(Node* root, int key, string value) {
    if (root == NULL) {
        return new Node(key, value);
    }
    if (key < root->key) {
        root->left = insert(root->left, key, value);
    } else {
        root->right = insert(root->right, key, value);
    }
    return root;
}

void Inorder(Node *root) {
    if (root == NULL) return;
    Inorder(root->left);
    cout << root->key << "-->" << root->value << endl;
    Inorder(root->right);
}

void Inorder_Rev(Node *root) {
    if (root == NULL) return;
    Inorder_Rev(root->right);
    cout << root->key << "-->" << root->value << endl;
    Inorder_Rev(root->left);
}

Node* search(Node* root, int target, int& comparisons) {
    if (root == NULL) {
        return NULL;
    }
    comparisons++;
    if (root->key == target) {
        return root;
    }
    if (target < root->key) {
        return search(root->left, target, comparisons);
    } else {
        return search(root->right, target, comparisons);
    }
}

int max_depth(Node *root) {
    if (root == NULL) return 0;
    return 1 + max(max_depth(root->left), max_depth(root->right));
}

void update(Node* root, int key, string new_val) {
    int comparisons = 0;
    Node* node = search(root, key, comparisons);
    if (node == NULL) {
        cout << "Key not found!" << endl;
    } else {
        node->value = new_val;
        cout << "Value updated for key " << key << " to: " << new_val << endl;
    }
}

Node* minValue(Node* node) {
    Node* current = node;
    while (current && current->left != NULL) {
        current = current->left;
    }
    return current;
}

Node* deleteNode(Node* root, int key) {
    if (root == NULL) return root;
    
    if (key < root->key) {
        root->left = deleteNode(root->left, key);
    } else if (key > root->key) {
        root->right = deleteNode(root->right, key);
    } else {
        if (root->left == NULL && root->right == NULL) {
            delete root;
            root = NULL;
        } else if (root->left == NULL) {
            Node* temp = root;
            root = root->right;
            delete temp;
        } else if (root->right == NULL) {
            Node* temp = root;
            root = root->left;
            delete temp;
        } else {
            Node* temp = minValue(root->right);
            root->key = temp->key;
            root->value = temp->value;
            root->right = deleteNode(root->right, temp->key);
        }
    }
    return root;
}

int main() {
    Node* root = NULL;
    int n, ch, i, key, target, comparisons;
    string value;
    
    Node* foundNode = NULL;  // Move this declaration outside the switch block
    comparisons = 0;         // Initialize comparisons variable
    
    while (true) {
        cout << "-----MENU-----" << endl;
        cout << "1. Insert\n2. Delete\n3. Update value\n4. Ascending Order\n5. Descending Order\n6. Search\n7. Exit\nEnter your choice:" << endl;
        cin >> ch;
        
        switch (ch) {
            case 1:
                cout << "How many nodes you want to enter: ";
                cin >> n;
                for (i = 0; i < n; i++) {
                    cout << "Enter key: ";
                    cin >> key;
                    cout << "Enter value: ";
                    cin >> value;
                    
                    comparisons = 0;
                    if (search(root, key, comparisons)) {
                        cout << "Key exists, enter new key. Key should be unique!" << endl;
                        break;
                    } else {
                        root = insert(root, key, value);
                    }
                }
                break;
                
            case 2:
                cout << "Enter key to be deleted: ";
                cin >> key;
                root = deleteNode(root, key);
                Inorder(root);
                break;
                
            case 3:
                cout << "Enter key for value change: ";
                cin >> key;
                cout << "Enter new value: ";
                cin >> value;
                update(root, key, value);
                Inorder(root);
                break;
                
            case 4:
                cout << "Tree in ascending order is: " << endl;
                Inorder(root);
                break;
                
            case 5:
                cout << "Tree in descending order is: " << endl;
                Inorder_Rev(root);
                break;
                
            case 6:
                cout << "Enter target to be searched: ";
                cin >> target;
                comparisons = 0;
                foundNode = search(root, target, comparisons);
                if (foundNode) {
                    cout << "Found target with comparisons: " << comparisons << endl;
                } else {
                    cout << "Target not found with comparisons: " << comparisons << endl;
                }
                break;
                
            case 7:
                cout << "Exited Program!" << endl;
                return 0;
                
            default:
                cout << "Wrong choice!" << endl;
        }
    }
}