#include <iostream>
#include <vector>
#include <limits>  // for numeric_limits<float>::max()
using namespace std;

float optimalBST(vector<int>& keys, vector<float>& prob, int n) {
    vector<vector<float>> cost(n, vector<float>(n, 0));
    vector<vector<float>> sum(n, vector<float>(n, 0));

    // Initialize cost and sum for single key
    for (int i = 0; i < n; i++) {
        cost[i][i] = prob[i];
        sum[i][i] = prob[i];
    }

    // Chains of length 2 to n
    for (int L = 2; L <= n; L++) {
        for (int i = 0; i <= n - L; i++) {
            int j = i + L - 1;
            cost[i][j] = numeric_limits<float>::max(); // ✅ Fixed

            // Calculate sum[i][j]
            sum[i][j] = sum[i][j - 1] + prob[j];

            // Try all roots in interval [i..j]
            for (int r = i; r <= j; r++) {
                float left = (r > i) ? cost[i][r - 1] : 0;
                float right = (r < j) ? cost[r + 1][j] : 0;
                float c = left + right + sum[i][j];

                if (c < cost[i][j])
                    cost[i][j] = c;
            }
        }
    }

    return cost[0][n - 1];
}

int main() {
    vector<int> keys = {10, 20, 30, 40}; // Sorted keys
    vector<float> prob = {0.1, 0.2, 0.4, 0.3}; // Probabilities
    int n = keys.size();

    float minCost = optimalBST(keys, prob, n);

    cout << "Minimum cost of Optimal BST: " << minCost << endl;
    return 0;
}
