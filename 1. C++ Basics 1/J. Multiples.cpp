#include <bits/stdc++.h>
using namespace std;
 
int main() {
    
    int a,b;
    cin >> a >> b;
    
    if(max(a,b)%min(a,b) == 0) cout << "Multiples" << endl;
    else cout << "No Multiples" << endl;
  
}
