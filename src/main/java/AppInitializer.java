import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class AppInitializer {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);


        while(true){
            System.out.println("============================");
            System.out.println("Welcome to the user interface");
            System.out.println("");
            System.out.println("Choose your choice:");
            System.out.println("1.Want to add User Go Here.");
            System.out.println("2.find a user with id");
            System.out.println("3.All users in the database");
            System.out.println("4.update user data in the database");
            System.out.println("5.delete user data in the database");
            System.out.println("6.Exit Here");
            System.out.println("");
            System.out.println("============================");

            System.out.print("Enter your choice:");
            String choice = scanner.next();
            scanner.nextLine();

            switch(choice){
                case "1":
                    System.out.print("id :");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("name :");
                    String name = scanner.nextLine();
                    System.out.print("email :");
                    String email = scanner.nextLine();
                    System.out.print("job :");
                    String job = scanner.nextLine();
                    System.out.print("salary :");
                    double salary = scanner.nextDouble();
                    System.out.println("");
                    UserEntity user = new UserEntity(id,name,email,job,salary);
                    if(saveUser(user)){
                        System.out.println("User added successfully");
                    }else {
                        System.out.println("User not added");
                    };
                    break;
                case "2":
                    System.out.println("Enter user id to find user :");
                    int userId = scanner.nextInt();
                    UserEntity oneUser = findCustomer(userId);
                    System.out.println(oneUser.toString());
                    break;

                case "3":
                    List<UserEntity> userEntityList = findAllUser();
                    userEntityList.stream().forEach(userEntity -> {
                        System.out.println(userEntity.toString());
                    });

                case "6":
                    System.out.println("Bye User");
                    return;
                default:
                    System.out.println("Please enter valid choice");
            }
        }

    }


//    create user
    private static boolean saveUser(UserEntity user){

      try{
          Session session = HibernateUtils.getSession();
          Transaction transaction = session.beginTransaction();
          session.save(user);
          transaction.commit();
      }catch(Exception e){
          System.out.println(e.getMessage());
      }
        return true;
    }

//    find one customer
    private static UserEntity findCustomer(int id){
        UserEntity user = null;
        try{
            Session session = HibernateUtils.getSession();
            user = session.find(UserEntity.class, id);
            if(user!=null){
                return user;
            }else{
                System.out.println("No user found that id");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return user;
    }

//    find all users
    private static List<UserEntity> findAllUser(){
        List<UserEntity> users = null;
        try{
            Session session = HibernateUtils.getSession();
            Query query = session.createQuery("FROM UserEntity", UserEntity.class);
            users = query.list();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return users;
    }
}
