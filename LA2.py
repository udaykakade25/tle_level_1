class Node:
    def __init__(self,data):
        self.data = data
        self.next = None
class HashTable:
    def __init__(self,size):
        self.size = size
        self.table = [None]*size
    def HashFunction(self,data):
        return data % self.size
    def Insert(self, data):
        index = self.HashFunction(data)

        new_node = Node(data)

        if self.table[index] == None:
            self.table[index] = new_node
        else:
            temp=self.table[index]
            while temp.next is not  None:
                temp = temp.next
            temp.next = new_node
        print(f"inserted data={data}, value={new_node.data} at index = {index}")
    def Search(self, data):
        index = self.HashFunction(data)
        temp = self.table[index]
        while temp is not None:
            if temp.data == data:
                print(f"Element found at index {index}")
                return True
            temp = temp.next
        print("Element not found")
        return False
    def Display(self):
        for i in range(self.size):
            temp = self.table[i]
            if temp is None:
                print(f"{i}\t {None}")
            else:
                chain = "" #empty string
                while temp != None:
                    chain += str(temp.data) + " -> "
                    temp = temp.next
                chain += "None"
                print(f"{i}\t {chain}")
    def Delete(self,data):
        index = self.HashFunction(data)
        temp = self.table[index]
        prev = None
        if not data:
            print("no element found to delete")
            return
        else:
            while temp is not None:
                if prev == None:
                    self.table[index] = temp.next # present at head 
                else:
                    prev.next= temp.next
                    print(f"element {data} deleted at pos {index}")
                prev = temp
                temp=temp.next

            


if __name__ == "__main__":
    h = HashTable(5)
    h.Insert(12)
    h.Insert(22)
    h.Insert(32)
    h.Display()
    h.Search(12)
    h.Search(99)
    h.Delete(32)

