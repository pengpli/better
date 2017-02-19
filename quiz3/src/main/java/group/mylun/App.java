package mylun;

public Class App{
  
  private static Map<String, Integer> State;
  private static Capacity;
  
  //when start, read the state from persitanted file on disk or database
  public State init(){
  }
  
  //persistant the state to disk when application close, and there is write/delete operation
  public void persistant(){
  }
  
  //return the id if create succeed
  //return if id already exit
  //return error if id has illeal character
  //return error if size overhead
  public String add(String id, Integer size){
  }
  
  //add multiple luns, return ids if succeed
  //throw error if id illegal or already exit
  //throw error if size overhead
  public String add(Map values){
  }
  
  //return 
  //throw error if id not exist
  //throw error if id in use
  //throw error if size overhead
  //throw error if size below zero or a given limit
  public String resize(String id, Integer size){
  }
  
  //return boolean
  //throw error if in sue
  //throw error if non_exist
  public boolean delete(String id){
  }
  
}
