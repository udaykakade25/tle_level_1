#include <bits/stdc++.h>
using namespace std;

long long fib(long long  n) {
   
    vector<long long> dp(n + 1);
    dp[0] = 0;
    dp[1] = 1;

    for (long long i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
    return dp[n-1];
}

int main() {
    long long n;
    cin >> n;

    cout << fib(n) << endl;
}
