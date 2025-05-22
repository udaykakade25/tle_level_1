#include <bits/stdc++.h>
using namespace std;

int main() {
   
       long long a,b,c,d;
       cin >> a  >> b >> c >> d;
       
       long long res = a*b%100;
       res = res*c%100;
       res = res*d%100;
       
       if(res <= 9) cout << "0" << res << endl;
       else
       cout << res << endl;
         
}
