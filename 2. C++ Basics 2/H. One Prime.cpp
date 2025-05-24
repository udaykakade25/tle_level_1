#include <bits/stdc++.h>
using namespace std;

int main() {
   
   int n;
   cin >> n;
   bool flag = true;
   
   
   for(int i=2; i<n; i++) {
      if(n%i == 0) {
         flag = false;
         break;
      }
   }
   if(flag || n == 2 || n==3) cout << "YES" << endl;
   else cout << "NO" << endl;

}
