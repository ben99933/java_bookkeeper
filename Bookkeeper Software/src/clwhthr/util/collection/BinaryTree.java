package clwhthr.util.collection;

import java.awt.color.ICC_ColorSpace;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class BinaryTree <T extends Comparable<T>> implements Iterable<T>{
	
	private int length;
	private Node root;
	
	public BinaryTree(){
		root = null;
		length = 0;
	}
	public int getLength() {
		return this.length;
	}
	public boolean isEmpty() {
		return this.length == 0;
	}
	public BinaryTree<T> add(T value){
		if(this.root == null) {
			root = new Node(value);
		}else {
			root.add(value);
		}
		this.length++;
		return this;
	}
	@Override
	public Iterator<T> iterator() {
		return new TreeIterator(this.root);
	}
}

class Node<T extends Comparable<T>>{
	T value;
	Node left;
	Node right;
	int times;
	public Node(T value) {
		this.value = value;
		left = null;
		right = null;
		times = 0;
	}
	public void add(T value) {
		int cmp = this.value.compareTo(value);
		//left
		if(cmp == 1) {
			if(this.left == null)this.left = new Node(value);
			else {
				this.left.add(value);
			}
		}else if(cmp == -1) {
			if(this.right == null)this.right = new Node(value);
			else {
				this.right.add(value);
			}
		}else {
			this.times++;
		}
	}
}

class TreeIterator<T extends Comparable<T>> implements Iterator<T>{
	
	Stack<T> stack = new Stack();
	Node root;
	public TreeIterator(Node root) {
		this.root = root;
		inOrderAddStack(root);
	}
	@Override
	public boolean hasNext() {
		return stack.size() != 0;
	}

	@Override
	public T next() {
		return stack.pop();
	}
	private void inOrderAddStack(Node node) {
		if(node.left != null)inOrderAddStack(node.left);
		
		for(int i = 0;i<node.times;i++)stack.add((T)node.value);
		
		if(node.right != null)inOrderAddStack(node.right);
	}
	
}