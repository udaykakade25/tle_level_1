#include <bits/stdc++.h>
using namespace std;

int main() {
   int t;
   cin >> t;
   
   while(t--) {
   int n;
   cin >> n;
   int arr[n];
   int odd=0, even=0;
   for(int i=0; i<n; i++) {
      cin >> arr[i];
      if(arr[i] & 1) odd++;
      else even++;
   }
   
   if(n&1) cout << -1 << endl;
   else {
      
     
        cout << abs(max(even,odd)-n/2) << endl;
      
      
      
      
   }
   
   
  }
    
}
