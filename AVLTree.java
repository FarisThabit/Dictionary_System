public class AVLTree<T extends Comparable<T>> extends BST<T> {
	
   protected int height;
	
	public AVLTree() {
		super();
		height = -1;
	}
	
	public AVLTree(BTNode<T> root) {
		super(root);
		height = -1;
	}
	
	public void purge(){
		super.purge();
	}
	
	public int getHeight() {
		return getHeight(root);
	}
	
	private int getHeight(BTNode<T> node) {
      if(node == null)
         return -1;
      else
         return 1 + Math.max(getHeight(node.left), getHeight(node.right));
   }
	
   private AVLTree<T> getLeftAVL() {
      AVLTree<T> leftsubtree = new AVLTree<T>(root.left);
      return leftsubtree;
   }

   private AVLTree<T> getRightAVL() {
      AVLTree<T> rightsubtree = new AVLTree<T>(root.right);
      return rightsubtree;
    }
    
	protected int getBalanceFactor() {
      if(isEmpty())
         return 0;
      else
         return getRightAVL().getHeight() - getLeftAVL().getHeight();
    }
    
    public void insertAVL(T el)  {
      super.insert(el);
      this.balance();
    }
    
    public void deleteAVL(T el) {
       
      root = deleteAVL(root, el);

    }

    private BTNode<T> deleteAVL(BTNode<T> node, T el) {

      if (node == null) {

         return null;

      }
          
      int cmp = el.compareTo(node.data);

      if (cmp < 0) {

         node.left = deleteAVL(node.left, el);
      } 

      else if (cmp > 0) {

         node.right = deleteAVL(node.right, el);
      } 

      else {
          
         if (node.left == null && node.right == null) {
             
            node = null;
         } 

         else if (node.left == null) {
             
            node = node.right;
         } 

         else if (node.right == null) {
             
            node = node.left;
         }

         else {
             
            BTNode<T> minNode = findMin(node.right);
            node.data = minNode.data;
            node.right = deleteAVL(node.right, minNode.data);
         }
      }

      if (node != null) {

         balance();

      }
          
      return node;

    }

    private BTNode<T> findMin(BTNode<T> node) {

      while (node.left != null) {

         node = node.left;

      }
          
      return node;

    }
    
    protected void balance() {

      if(!isEmpty())
      {
         getLeftAVL().balance();
    	   getRightAVL().balance();

         adjustHeight();
        
         int balanceFactor = getBalanceFactor();
        
         if(balanceFactor == -2) {
			System.out.println("Balance factor = " + balanceFactor);
			System.out.println("Balancing node with el: "+root.data);
			
			
			if(getRightAVL().getBalanceFactor() == 0 && getLeftAVL().getBalanceFactor() == -1)
				  rotateRight();                                
            else if(getLeftAVL().getBalanceFactor() <= 0)     
			      rotateRight();
            else
               rotateLeftRight();
         }
		
         else if(balanceFactor == 2) {
			System.out.println("Balance factor = " + balanceFactor);
            System.out.println("Balancing node with el: "+root.data);
			
			if(getRightAVL().getBalanceFactor() == 0)
				  rotateLeft();                                
            else if(getRightAVL().getBalanceFactor() > 0)   
               rotateLeft();
            else
               rotateRightLeft();
         }
      }
   }
    
   protected void adjustHeight()
   {
      if(isEmpty())
         height = -1;
      else
         height = 1 + Math.max(getLeftAVL().getHeight(), getRightAVL().getHeight());   
   }
    
   protected void rotateRight() {

	   System.out.println("RIGHT ROTATION");

      BTNode<T> newRoot = root.left;
      root.left = newRoot.right;
      newRoot.right = root;
      return;
      	
   }
    
   protected void rotateLeft() {
 	  System.out.println("LEFT ROTATION");
      BTNode<T> tempNode = root.left;
      root.left = root.right;
      root.right = root.left.right;
      root.left.right = root.left.left;
      root.left.left = tempNode;
            
      T val = (T) root.data;
      root.data = root.left.data;
      root.left.data = val;
            
      getLeftAVL().adjustHeight();
      adjustHeight();
	}
	
	protected void rotateLeftRight() {

      System.out.println("Double Rotation...");
 	   getLeftAVL().rotateLeft();
      getLeftAVL().adjustHeight();
      this.rotateRight();
      this.adjustHeight();
  }

   protected void rotateRightLeft() {

	   System.out.println("Double Rotation...");
       
      getRightAVL().rotateRight();
      getRightAVL().adjustHeight();
      rotateLeft();
      adjustHeight();
	  
   }
   
   public void levelOrderTraversal(){
	   levelOrderTraversal(root);
   }
      
}