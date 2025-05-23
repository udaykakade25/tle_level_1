#include <bits/stdc++.h>
using namespace std;

int main() {
   
   double n;
   cin >> n;
   
   int num = n;
   
  if(ceil(n) == num) {
   cout << "int" << " "<< num << endl;
  }
  else{
    
    cout << "float" << " " << num << " " << n - num << endl;
   
  }
  
}
