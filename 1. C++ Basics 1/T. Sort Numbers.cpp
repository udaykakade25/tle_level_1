#include <bits/stdc++.h>
using namespace std;

int main() {
    
    int a,b,c;
    cin >> a >> b >> c;
    int arr[3];
    arr[0] = a, arr[1] = b, arr[2] = c;
    
    sort(arr,arr+3);
    
    for(int i=0; i<3; i++) {
        cout << arr[i] << endl;
    }
    cout << endl;
    
    cout << a << endl;
    cout << b << endl;
    cout << c << endl;
    
    
}
