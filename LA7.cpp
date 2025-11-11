#include <iostream>
#include <unordered_map>
#include <vector>
#include <list>
#include <set>
using namespace std;

class FlightGraph {
private:
    unordered_map<string, list<pair<string, double>>> adjList;

    void dfs(const string& city, set<string>& visited) {
        visited.insert(city);
        for (auto& neighbor : adjList[city]) {
            if (visited.find(neighbor.first) == visited.end()) {
                dfs(neighbor.first, visited);
            }
        }
    }

public:
    // Add a flight between two cities with time cost
    void addFlight(const string& city1, const string& city2, double time) {
        adjList[city1].push_back({city2, time});
        adjList[city2].push_back({city1, time}); // Assuming undirected (bidirectional flight)
    }

    // Display adjacency list
    void display() {
        for (const auto& pair : adjList) {
            cout << pair.first << " -> ";
            for (const auto& neighbor : pair.second) {
                cout << "(" << neighbor.first << ", " << neighbor.second << "h) ";
            }
            cout << endl;
        }
    }

    // Check if the graph is connected
    bool isConnected() {
        if (adjList.empty()) return true;

        set<string> visited;
        auto start = adjList.begin()->first;
        dfs(start, visited);

        return visited.size() == adjList.size();
    }
};

int main() {
    FlightGraph g;
    g.addFlight("Mumbai", "Delhi", 2.0);
    g.addFlight("Delhi", "Bangalore", 2.5);
    g.addFlight("Mumbai", "Chennai", 3.0);
    g.addFlight("Kolkata", "Chennai", 2.0);

    cout << "Adjacency List Representation:\n";
    g.display();

    if (g.isConnected())
        cout << "\nThe graph is connected.\n";
    else
        cout << "\nThe graph is NOT connected.\n";

    return 0;
}
