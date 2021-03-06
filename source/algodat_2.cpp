/*

Karoline Brehm
117190

SS2017

Algorithmen und Datenstrukturen - Beleg 2

*/

#include <vector>
#include <algorithm>
#include <iostream>
#include <sstream>
#include <string>
#include <iterator>

template<typename T>
void swap (std::vector<T> & v, int first, int second)
{
	T temp = v[first];
	v[first] = v[second];
	v[second] = temp;
}

//returns vector with unsorted input read from command line
template<typename T>
std::vector<T> read_input ()
{
	//emtpy vector to hold unsorted elements
	std::vector<T> output;

	//request user input
	std::cout << "\n";
	std::cout << "Enter list: \n";
	std::cout << "(Format: list seperated by whitespace) like this:\n";
	std::cout << " 2 5 66 345 1 or: z a l r p )\n";

	//write input to string "input"
	std::string input;
	//user input ends
	//with pressing enter key:
	std::getline(std::cin, input);

	//use stringstream to extract ints from input
	T value;
	std::stringstream ss;
	//puts input in stringstream
	ss.str(input);

	//reads from stringstream like from std::cin
	while (ss >> value)
	{
		//push single ints to end of vector
		output.push_back(value);
	}
	std::cout << "\n";

	return output;
}

/*
function that heapifies the given vector
(i decided to not use a vector container, as
their random acess is less efficient)
*/
template<typename T>
std::vector<T> heapify(std::vector<T> unsorted)
{
	std::vector<T> heap;
	
	/*
	iterates over unsorted vector and adds 
	each element to heap-vector, and sorts it
	O(n) * O(log2(n)) with n = number of elements
	*/
	for (T el : unsorted)
	{
		//add element O(c) to end of vector
		heap.push_back(el);

		//compute index of new element
		int current = heap.size() - 1;

		/*
		compare the new element to it's parent
		element and swap them if it is bigger, until
		correct position in heap is found, and vector
		meets heap criteria
		if the new element is the biggest element
		in the heap, its index == 0, and the loop
		ends as well
		O(log2 n) as heap has log2(n) levels (?)
		*/
		bool sorted = false;
		while(current > 0 and sorted == false)
		{
			//compute parent index (which for
			//indices > 0 will always be in the
			//range of the vector)
			int parent = 0;
			if (current % 2 != 0)
			{
				parent = current / 2;
			}
			else
				parent = current / 2 - 1;

			//if current > parent, swap
			if (heap[current] > heap[parent])
			{
				//swap the new and it's parent
				//element
				swap(heap, current, parent);

				//the new element has now the index
				//of it's former parent element
				current = parent;
			}
			else
				sorted = true;
		} //end while heap not sorted
	}//end for each el in unsorted
	return heap;
}

int main()
{

//INTS

	//get user input INTS 
	std::cout << "\n enter some ints! \n";
	std::vector<int> input_test = read_input<int>();
	
	//print int input
	std::copy(input_test.begin(), input_test.end(),
		std::ostream_iterator<int>(std::cout, " "));
	std::cout << "\n ";

	//heap sort the vector
	std::vector<int> v = heapify(input_test);	

	//print heap
	std::copy(v.begin(), v.end(),
		std::ostream_iterator<int>(std::cout, " "));
	std::cout << "\n ";

//CHARS

	//get user input CHARS
	std::cout << "\n enter some chars! \n";
	std::vector<char> char_input = read_input<char>();
	
	//print user input
	std::copy(char_input.begin(), char_input.end(),
		std::ostream_iterator<char>(std::cout, " "));
	std::cout << "\n ";

	//heap sort the vector
	std::vector<char> w = heapify(char_input);	

	//print heap
	std::copy(w.begin(), w.end(),
		std::ostream_iterator<char>(std::cout, " "));
	std::cout << "\n ";
}