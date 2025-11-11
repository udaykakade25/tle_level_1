#include <iostream>
using namespace std;

struct Node {
    int key;
    int value;
    Node* left;
    Node* right;
    int height;

    Node(int k, int v) {
        key = k;
        value = v;
        left = right = nullptr;
        height = 1;
    }
};

int getHeight(Node* n) {
    return n ? n->height : 0;
}

int getBalance(Node* n) {
    return n ? getHeight(n->left) - getHeight(n->right) : 0;
}

int max(int a, int b) {
    return (a > b) ? a : b;
}

Node* rightRotate(Node* y) {
    Node* x = y->left;
    Node* T2 = x->right;

    x->right = y;
    y->left = T2;

    y->height = 1 + max(getHeight(y->left), getHeight(y->right));
    x->height = 1 + max(getHeight(x->left), getHeight(x->right));

    return x;
}

Node* leftRotate(Node* x) {
    Node* y = x->right;
    Node* T2 = y->left;

    y->left = x;
    x->right = T2;

    x->height = 1 + max(getHeight(x->left), getHeight(x->right));
    y->height = 1 + max(getHeight(y->left), getHeight(y->right));

    return y;
}

Node* insert(Node* node, int key, int value) {
    if (!node) return new Node(key, value);

    if (key < node->key)
        node->left = insert(node->left, key, value);
    else if (key > node->key)
        node->right = insert(node->right, key, value);
    else {
        node->value = value;  // Update if exists
        return node;
    }

    node->height = 1 + max(getHeight(node->left), getHeight(node->right));
    int balance = getBalance(node);

    if (balance > 1 && key < node->left->key)
        return rightRotate(node);

    if (balance < -1 && key > node->right->key)
        return leftRotate(node);

    if (balance > 1 && key > node->left->key) {
        node->left = leftRotate(node->left);
        return rightRotate(node);
    }

    if (balance < -1 && key < node->right->key) {
        node->right = rightRotate(node->right);
        return leftRotate(node);
    }

    return node;
}

Node* minValueNode(Node* node) {
    while (node->left != nullptr)
        node = node->left;
    return node;
}

Node* deleteNode(Node* root, int key) {
    if (!root) return root;

    if (key < root->key)
        root->left = deleteNode(root->left, key);
    else if (key > root->key)
        root->right = deleteNode(root->right, key);
    else {
        if (!root->left || !root->right) {
            Node* temp = root->left ? root->left : root->right;
            if (!temp) {
                temp = root;
                root = nullptr;
            } else
                *root = *temp;
            delete temp;
        } else {
            Node* temp = minValueNode(root->right);
            root->key = temp->key;
            root->value = temp->value;
            root->right = deleteNode(root->right, temp->key);
        }
    }

    if (!root) return root;

    root->height = 1 + max(getHeight(root->left), getHeight(root->right));
    int balance = getBalance(root);

    if (balance > 1 && getBalance(root->left) >= 0)
        return rightRotate(root);

    if (balance > 1 && getBalance(root->left) < 0) {
        root->left = leftRotate(root->left);
        return rightRotate(root);
    }

    if (balance < -1 && getBalance(root->right) <= 0)
        return leftRotate(root);

    if (balance < -1 && getBalance(root->right) > 0) {
        root->right = rightRotate(root->right);
        return leftRotate(root);
    }

    return root;
}

Node* update(Node* root, int key, int newValue) {
    if (!root) return nullptr;

    if (key == root->key) {
        root->value = newValue;
    } else if (key < root->key) {
        update(root->left, key, newValue);
    } else {
        update(root->right, key, newValue);
    }
    return root;
}

pair<Node*, int> search(Node* root, int key) {
    int comparisons = 0;
    while (root) {
        comparisons++;
        if (key == root->key)
            return {root, comparisons};
        else if (key < root->key)
            root = root->left;
        else
            root = root->right;
    }
    return {nullptr, comparisons};
}

void inorder(Node* root) {
    if (root) {
        inorder(root->left);
        cout << root->key << " : " << root->value << endl;
        inorder(root->right);
    }
}

void reverseInorder(Node* root) {
    if (root) {
        reverseInorder(root->right);
        cout << root->key << " : " << root->value << endl;
        reverseInorder(root->left);
    }
}

// Main dictionary interface
int main() {
    Node* root = nullptr;
    int choice, key, value;

    do {
        cout << "\n=== Dictionary Menu ===\n";
        cout << "1. Add/Update Keyword\n2. Delete Keyword\n3. Search Keyword\n";
        cout << "4. Update Value\n5. Display Ascending\n6. Display Descending\n";
        cout << "7. Exit\nEnter choice: ";
        cin >> choice;

        switch (choice) {
        case 1:
            cout << "Enter Key (int): ";
            cin >> key;
            cout << "Enter Value (int): ";
            cin >> value;
            root = insert(root, key, value);
            break;
        case 2:
            cout << "Enter Key to Delete: ";
            cin >> key;
            root = deleteNode(root, key);
            break;
        case 3:
            cout << "Enter Key to Search: ";
            cin >> key;
            {
                auto res = search(root, key);
                if (res.first)
                    cout << "Found: " << res.first->key << " -> " << res.first->value
                         << " (Comparisons: " << res.second << ")\n";
                else
                    cout << "Not Found (Comparisons: " << res.second << ")\n";
            }
            break;
        case 4:
            cout << "Enter Key to Update: ";
            cin >> key;
            cout << "Enter New Value: ";
            cin >> value;
            root = update(root, key, value);
            break;
        case 5:
            cout << "Ascending Order:\n";
            inorder(root);
            break;
        case 6:
            cout << "Descending Order:\n";
            reverseInorder(root);
            break;
        case 7:
            cout << "Exiting...\n";
            break;
        default:
            cout << "Invalid choice.\n";
        }
    } while (choice != 7);

    return 0;
}
