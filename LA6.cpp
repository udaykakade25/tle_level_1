#include <iostream>
#include <vector>
#include <queue>
#include <map>
using namespace std;

const int NUM_NODES = 6;

// Landmark labels
map<int, string> landmark = {
    {0, "College"},
    {1, "Canteen"},
    {2, "Library"},
    {3, "Hostel"},
    {4, "Gym"},
    {5, "Bookstore"}
};

// DFS using Adjacency Matrix
void DFS_matrix(vector<vector<int>>& adjMatrix, vector<bool>& visited, int node) {
    visited[node] = true;
    cout << landmark[node] << " ";

    for (int i = 0; i < NUM_NODES; i++) {
        if (adjMatrix[node][i] == 1 && !visited[i]) {
            DFS_matrix(adjMatrix, visited, i);
        }
    }
}

// BFS using Adjacency List
void BFS_list(vector<vector<int>>& adjList, int start) {
    vector<bool> visited(NUM_NODES, false);
    queue<int> q;

    visited[start] = true;
    q.push(start);

    while (!q.empty()) {
        int current = q.front();
        q.pop();
        cout << landmark[current] << " ";

        for (int neighbor : adjList[current]) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                q.push(neighbor);
            }
        }
    }
}

int main() {
    // Adjacency Matrix for DFS
    vector<vector<int>> adjMatrix(NUM_NODES, vector<int>(NUM_NODES, 0));

    // Adjacency List for BFS
    vector<vector<int>> adjList(NUM_NODES);

    // Graph Connections
    vector<pair<int, int>> edges = {
        {0, 1}, {0, 2},
        {2, 3}, {1, 4},
        {4, 5}, {3, 5}
    };

    for (auto edge : edges) {
        int u = edge.first;
        int v = edge.second;

        adjMatrix[u][v] = 1;
        adjMatrix[v][u] = 1;

        adjList[u].push_back(v);
        adjList[v].push_back(u);
    }

    // DFS from College (node 0)
    cout << "DFS Traversal (Adjacency Matrix): ";
    vector<bool> visited(NUM_NODES, false);
    DFS_matrix(adjMatrix, visited, 0);
    cout << endl;

    // BFS from College (node 0)
    cout << "BFS Traversal (Adjacency List): ";
    BFS_list(adjList, 0);
    cout << endl;

    return 0;
}
