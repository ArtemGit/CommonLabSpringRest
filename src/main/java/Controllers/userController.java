package Controllers;

import java.util.List;

import dao.daoGroupMembers;
import dao.userDao;
import entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
public class userController {


    userDao userService;  //Service which will do all data retrieval/manipulation work

    daoGroupMembers groupUserService;  //Service which will do all data retrieval/manipulation work

    //-------------------Retrieve All Users--------------------------------------------------------

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<Users>> listAllUsers() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (userDao) ctx.getBean("userDao");
        List<Users> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<List<Users>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Users>>(users, HttpStatus.OK);
    }


    //-------------------Retrieve Single User--------------------------------------------------------

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> getUser(@PathVariable("username") String username) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (userDao) ctx.getBean("userDao");
        System.out.println("Fetching User with id " + username);
        Users user = userService.findByUserName(username);
        if (user == null) {
            System.out.println("User with id " + username + " not found");
            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Users>(user, HttpStatus.OK);
    }


    //-------------------Create a User--------------------------------------------------------

    @RequestMapping(value = "/user/{username}", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Users user, UriComponentsBuilder ucBuilder) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (userDao) ctx.getBean("userDao");
        groupUserService = (daoGroupMembers) ctx.getBean("daoGroupMembers");
        System.out.println("Creating User " + user.getUsername());
        if (userService.isUserExist(user)) {
            System.out.println("Пользователь с логином" + user.getUsername() + " уже существует");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        user.setEnabled((byte) 1);
        userService.saveUser(user);
        groupUserService.addUserToGroup(user.getUsername(), 2);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{username}").buildAndExpand(user.getUsername()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a User --------------------------------------------------------

    @RequestMapping(value = "/user/{username}", method = RequestMethod.PUT)
    public ResponseEntity<Users> updateUser(@PathVariable("username") String username, @RequestBody Users user) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (userDao) ctx.getBean("userDao");
        System.out.println("Updating User " + username);
        if (!userService.isUserExist(user)) {
            System.out.println("User with username " + username + " not found");
            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Updating User 11" + user.getUsername() + " " + user.getEmail());
        if (userService.updateUser(user)) {
            System.out.println("Updating User 22" + username);
            return new ResponseEntity<Users>(user, HttpStatus.OK);
        } else return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
    }


    //------------------- Delete a User --------------------------------------------------------

    @RequestMapping(value = "/user/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<Users> deleteUser(@PathVariable("username") String username) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (userDao) ctx.getBean("userDao");
        System.out.println("Fetching & Deleting User with id " + username);

        Users user = userService.findByUserName(username);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + username + " not found");
            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
        }
        if (userService.deleteUserByUserName(user))
            return new ResponseEntity<Users>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
    }

    /*
        //------------------- Delete All Users --------------------------------------------------------

        @RequestMapping(value = "/user", method = RequestMethod.DELETE)
        public ResponseEntity<user> deleteAllUsers() {
            System.out.println("Deleting All Users");

            userService.deleteAllUsers();
            return new ResponseEntity<user>(HttpStatus.NO_CONTENT);
        }
    */
}