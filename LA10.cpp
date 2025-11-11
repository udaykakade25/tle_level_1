#include <iostream>
#include <vector>
#include <queue>  // for priority_queue
using namespace std;

int main() {
    int n;
    cout << "Enter number of students: ";
    cin >> n;

    vector<int> marks(n);
    cout << "Enter marks of " << n << " students:\n";
    for (int i = 0; i < n; i++) {
        cin >> marks[i];
    }

    // Max-Heap for finding maximum
    priority_queue<int> maxHeap;
    // Min-Heap for finding minimum (using greater<int>)
    priority_queue<int, vector<int>, greater<int>> minHeap;

    for (int mark : marks) {
        maxHeap.push(mark);
        minHeap.push(mark);
    }

    int maxMark = maxHeap.top();
    int minMark = minHeap.top();

    cout << "\nMaximum Marks = " << maxMark << endl;
    cout << "Minimum Marks = " << minMark << endl;

    return 0;
}
