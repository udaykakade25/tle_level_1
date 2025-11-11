#include <iostream>
#include <climits>
using namespace std;
struct Node
{
    int data;
    Node *left;
    Node *right;
    Node(int value)
    {
        data=value;
        left=nullptr;
        right=nullptr;
    }
};
Node *insert(Node *root,int value)
{
    if(root==nullptr)
    return new Node(value);
    else if(value < root->data)
    root->left=insert(root->left,value);
    else
    root->right=insert(root->right,value);
    return root;
}
void inorder(Node *root)
{
    if(root==nullptr)
    return;
    inorder(root->left);
    cout<<root->data<<" ";
    inorder(root->right);
}
void preorder(Node *root)
{
    if(root==nullptr)
    return;
    cout<<root->data<<" ";
    preorder(root->left);
    preorder(root->right);
}
void postorder(Node *root)
{
    if(root==nullptr)
    return;
    postorder(root->left);
    postorder(root->right);
    cout<<root->data<<" ";
}
int longest(Node *root)
{
    if(root==nullptr)
    return 0;
    int lcnt=longest(root->left);
    int rcnt=longest(root->right);
    return max(lcnt,rcnt)+1;
}
int minimum(Node *root)
{
    if(root==nullptr)
    return INT_MAX;
    int leftmin=minimum(root->left);
    int rightmin=minimum(root->right);
    return min(root->data,min(leftmin,rightmin));
}
void swap_node(Node *root)
{
    if(root==nullptr)
    return;
    Node *temp=root->left;
    root->left=root->right;
    root->right=temp;
    swap_node(root->left);
    swap_node(root->right);
}
Node* search(Node *root,int target)
{
    if(root==nullptr||root->data==target)
    return root;
    if(target<root->data)
    return search(root->left,target);
    else
    return search(root->right,target);
}
int main()
{
    int ch;
    Node *root=nullptr;
    root=insert(root,30);
    root=insert(root,12);
    root=insert(root,40);
    root=insert(root,7);
    root=insert(root,60);
    root=insert(root,70);
    root=insert(root,8);
    cout<<"Inorder Traversal: ";
    inorder(root);
    cout<<endl;
    cout<<"Preorder Traversal: ";
    preorder(root);
    cout<<endl;
    cout<<"Postorder Traversal: ";
    postorder(root);
    cout<<endl;
    while(true)
    {
        cout<<"\nEnter your choice:\n1. Insert a node\n2. No of nodes in longest chain\n3. Minimum value\n4. Swap Tree\n5. Seach a value\n6. Exit\n";
        cin>>ch;
        if(ch==1)
        {
            int x;
            cout<<"Enter a value: ";
            cin>>x;
            root=insert(root,x);
        }
        else if(ch==2)
        {
            int maximum=longest(root);
            cout<<"No of nodes in longest chain is: "<<maximum;
        }
        else if(ch==3)
        {
            int mini=minimum(root);
            cout<<"Minimum element: "<<mini;
        }
        else if(ch==4)
        {
            swap_node(root);
            cout<<"Tree after swapping: Inorder Traversal: ";
            inorder(root);
        }
        else if(ch==5)
        {
            int target;
            cout<<"Enter value to be searched: ";
            cin>>target;
            Node* result=search(root,target);
            if(result!=nullptr)
            cout<<"Key found!";
            else
            cout<<"Key not found!";
        }
        else if(ch==6)
        {
            cout<<"Exited Program!";
            break;
        }
    }
    return 0;
}