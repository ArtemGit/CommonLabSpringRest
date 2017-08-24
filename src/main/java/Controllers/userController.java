package Controllers;

import dao.daoGroupMembers;
import dao.userDao;
import entities.Users;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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
    }    //-------------------Retrieve All Clients--------------------------------------------------------

    @RequestMapping(value = "/user/clientsList", method = RequestMethod.GET)
    public ResponseEntity<List<Users>> listAllClients() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (userDao) ctx.getBean("userDao");
        List<Users> users = userService.findAllClients();
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        user.setDate(dateFormat.format(new Date()));
        userService.saveUser(user);
        groupUserService.addUserToGroup(user.getUsername(), 1);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{username}").buildAndExpand(user.getUsername()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/registrate/{username}", method = RequestMethod.POST)
    public ResponseEntity<Void> registrateUserClient(@RequestBody Users user, UriComponentsBuilder ucBuilder) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (userDao) ctx.getBean("userDao");
        groupUserService = (daoGroupMembers) ctx.getBean("daoGroupMembers");
        HttpHeaders headers = new HttpHeaders();
        System.out.println("Creating User " + user.getPhone());
        if (userService.findByUserName(user.getPhone())!= null) {
            System.out.println("Пользователь с логином/телефоном" + user.getPhone() + " уже существует");
            headers.add("x-message", "Пользователь с логином/телефоном" + user.getPhone() + " уже существует");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        Users newUser = new Users();
        newUser.setUsername(user.getPhone());
        newUser.setPhone(user.getPhone());
        newUser.setName(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        newUser.setDate(dateFormat.format(new Date()));
        newUser.setEnabled((byte) 1);
        if(userService.saveUser(newUser)) {
            groupUserService.addUserToGroup(newUser.getUsername(), 3);

            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        }
        else return new ResponseEntity<Void>(headers, HttpStatus.CONFLICT);
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
        System.out.println("Updating User " + user.getUsername() + " " + user.getEmail());
        if (userService.updateUser(user)) {
            System.out.println("Updating User " + username);
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