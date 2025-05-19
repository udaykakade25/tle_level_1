#include <bits/stdc++.h>
using namespace std;

int main() {
    
    int t;
    cin >> t;
    while(t--) {
        
        long long a , b;
        cin >> a >> b;
        long long aa = min(a,b);
        long long bb = max(a,b);
        long long sum1 = aa*(aa-1)/2;
        long long sum2 = bb*(bb+1)/2;
        
        cout << sum2 - sum1  << endl;
        
     
    }
    
    
}
