interface Tree<T> {
  public <U> U accept(TreeVisitor<T, U> v);
}

class Leaf<T> implements Tree<T>{
  public final T value;
 
  public Leaf(T value) {
    this.value = value;
  }

  public <U> U accept(TreeVisitor<T, U> v) {
    return v.visitLeaf(this);
  }
}

class Node<T> implements Tree<T> {
  public final Tree<T> left;
  public final Tree<T> right;

  public Node(Tree<T> left, Tree<T> right) {
    this.left = left;
    this.right = right;
  }

  public <U> U accept(TreeVisitor<T, U> v) {
    return v.visitNode(this);
  }
}

interface TreeVisitor<T, U> {
  public U visitLeaf(Leaf<T> t);
  public U visitNode(Node<T> t);
}

public class CorrectedVisitor {
  public static void main(String[] argv) {
    Tree<String> tree = new Node<String>(
      new Leaf<String>("Hello,"),
      new Leaf<String>(" world!")
    );

    TreeVisitor<String, String> visitor = new TreeVisitor<>() {
      public String visitLeaf(Leaf<String> t) {
        return t.value;
      }

      public String visitNode(Node<String> t) {
        return t.left.accept(this) + t.right.accept(this);
      }
    };

    System.out.println(tree.accept(visitor));
  }
}
