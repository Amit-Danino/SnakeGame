import java.awt.*;

public class Snake {

    static int score;
    static int snacks;
    static int speed;
    static boolean automatic;
    static boolean withBoundaries;
    static boolean foodEaten;

    public static void main(String[] args){
        gameInitialization();
        List snakeParts = initializeSnake();
        List snakeFood = initializeFood(snakeParts,snacks);

        if(automatic) autoplay(0.5,0.5,'s',snakeParts,snakeFood);
        else{
            manualPlayEfficient(0.5,0.5,'s',snakeParts,snakeFood);
        }
    }

    /**
     * Initializing the snake game according to the user's wishes.
     */
    public static void gameInitialization(){
        //User's decision to play manually or automatically
        System.out.println("Would you like to play manually or automatically?");
        while(true) {
            String input1 = StdIn.readString();
            if (input1.equals("manually")){
                automatic = false;
                break;
            }
            else if (input1.equals("automatically")) {
                automatic = true;
                break;
            }
            else {
                System.out.println("Invalid input. Please answer with 'manually' or 'automatically'");
            }
        }

        //Attempts to initialize the snake food's List according to the user's input.
        System.out.println("How many snake snacks would you like on the board?");
        while(true){
            try{
                snacks = StdIn.readInt();
                break;
            }
            catch (Exception e){
                System.out.println("Invalid input. Try inserting a number as the first argument.");
            }

        }

        /*
            Checking whether the user wants the snake to be able to pass through the walls or not.
            Also checking if the input is valid.
        */
        System.out.println("Would you like to be able to pass through walls? (answer with 'Yes' or 'No')");
        while(true){
            String walls = StdIn.readString();
            if (walls.equals("Yes")){
                withBoundaries = false;
                break;
            }
            else if(walls.equals("No")){
                withBoundaries = true;
                break;
            }
            System.out.println("Invalid input, please answer with 'Yes' or 'No'");
        }
        /*
            Lets the user decide the snake's speed.
         */
        System.out.println("From 1-10 - choose the snake's speed! (1 is fastest, 10 is slowest)");
        while(true){
            try{
                speed = StdIn.readInt();
                if(speed > 10 || speed < 1){
                    System.out.println("Invalid input. Try inserting a number from 1-10.");
                    continue;
                }
                break;
            }
            catch (Exception e){
                System.out.println("Invalid input. Try inserting a number from 1-10.");
            }
        }
        speed *= 50;
    }


    /**
     * Initializing the snake's Linked List.
     * Starts as size 3.
     * @return List
     */
    public static List initializeSnake(){
        List snakeParts = new List();
        snakeParts.insertLast(0.5,0.6);
        snakeParts.insertLast(0.5,0.55);
        snakeParts.insertLast(0.5,0.5);
        return snakeParts;
    }


    /**
     * Initializing the snake food's Linked List.
     * @param snakeParts : The snake's Linked List.
     * @param amount : The amount of snake food to be inserted according to the user.
     * @return List
     */
    public static List initializeFood(List snakeParts, int amount){
        List snakeFood = new List();
        for (int i = 0 ; i <amount ; i++){
            Node food = snakeFood(snakeParts);
            snakeFood.insertLast(food.x,food.y);
        }
        return snakeFood;
    }


    /**
     * Automaticcaly starts playing the snake game according to the user's input received in the arguments array.
     * @param x : current x location of the snake's head.
     * @param y : current y location of the snake's head.
     * @param c : current direction of the snake represented by a char
     * @param snakeParts : the snake's Linked List
     * @param snakeFood : the snake food's Linked List
     */
    public static void autoplay(double x, double y, char c, List snakeParts, List snakeFood){
        pause(speed);
        StdDraw.clear();

        StdDraw.setPenColor(0,0,255);
        StdDraw.text(0.50,1,"Score : "+score);
        StdDraw.setPenColor(0,0,0);

        Node snakehead = snakeParts.first.next;
        StdDraw.filledSquare(snakehead.x,snakehead.y,0.024);
        Node snakefoods = snakeFood.first.next;
        for (int i = 0 ; i < snakeFood.size ;i++){
            StdDraw.filledSquare(snakefoods.x,snakefoods.y,0.024);
            snakefoods = snakefoods.next;
        }
        while (snakehead!=null){
            if (snakehead.next!= null) StdDraw.filledSquare(snakehead.x,snakehead.y,0.024);
            else{
                StdDraw.setPenColor(0,0,255);
                StdDraw.filledSquare(snakehead.x,snakehead.y,0.024);
            }
            snakehead = snakehead.next;
        }
        boolean up = false;
        boolean left = false;
        boolean down = false;
        boolean right = false;
        snakehead = snakeParts.first.next;

        //checking in which direction can the snake move in the next iteration.
        if (!withBoundaries) {
            while (snakehead != null) {
                if (Math.abs(snakehead.x - x) < 0.001 && (Math.abs(snakehead.y - y - 0.05) < 0.001 || Math.abs(snakehead.y - y - 0.05 + 1) < 0.001))
                    up = true;
                else if (Math.abs(snakehead.x - x) < 0.001 && (Math.abs(snakehead.y - y + 0.05) < 0.001 || Math.abs(snakehead.y - y + 0.05 - 1) < 0.001))
                    down = true;
                else if ((Math.abs(snakehead.x - x - 0.05) < 0.001 || Math.abs(snakehead.x - x - 0.05 + 0.95) < 0.001) && Math.abs(snakehead.y - y) < 0.001)
                    right = true;
                else if ((Math.abs(snakehead.x - x + 0.05) < 0.001 || Math.abs(snakehead.x - x + 0.05 - 0.95) < 0.001) && Math.abs(snakehead.y - y) < 0.001)
                    left = true;
                snakehead = snakehead.next;
            }
        } else {
            while (snakehead != null) {
                if (Math.abs(snakehead.x - x) < 0.001 && (Math.abs(snakehead.y - y - 0.05) < 0.001 || Math.abs(snakehead.y - y - 0.05 + 1) < 0.001))
                    up = true;
                else if (Math.abs(snakehead.x - x) < 0.001 && (Math.abs(snakehead.y - y + 0.05) < 0.001 || Math.abs(snakehead.y - y + 0.05 - 1) < 0.001))
                    down = true;
                else if (Math.abs(snakehead.x - x - 0.05) < 0.001 && Math.abs(snakehead.y - y) < 0.001)
                    right = true;
                else if (Math.abs(snakehead.x - x + 0.05) < 0.001 && Math.abs(snakehead.y - y) < 0.001) left = true;
                snakehead = snakehead.next;
            }
            if (x < 0.001) left = true;
            if (y < 0.001) down = true;
            if (x - 0.95 > 0.001) right = true;
            if (y - 0.95 > 0.001) up = true;
        }
        //rolling a valid direction for the snake's next iteration.
        c = randChar(c,up,left,down,right);
        //incrementing coordinates according to the character rolled in randChar.
        if (c == 'w') y += 0.05;
        else if (c == 'a') x -= 0.05;
        else if (c == 's') y -= 0.05;
        else if (c == 'd') x += 0.05;

        if(!withBoundaries){
            if (x>1)x -= 1;
            else if (x<0)x += 1;
            else if (y>1)y -= 1;
            else if (y<0)y += 1;
        }

        //checking if the snake is crashing into itself
        Node crashTest = snakeParts.first.next.next;
        while (crashTest!=null){
            if (crashTest.x==x && crashTest.y==y){
                System.out.println("YOU LOSE (Crashed into yourself)");
                System.out.println("Score = "+score);
                return;
            }
            crashTest = crashTest.next;
        }
        //checking if the snake reached food. if it did, eat the food, increase score and extend the snake by 1 size.
        boolean snakeAteSomething = false;
        snakeParts.insertLast(x,y);
        snakefoods = snakeFood.first.next;
        for (int i = 0 ; i < snakeFood.size ; i++){
            if (Math.abs(snakefoods.x-x)<0.001 && Math.abs(snakefoods.y-y)<0.001){
                Node food = snakeFood(snakeParts);
                snakefoods.x = food.x;
                snakefoods.y = food.y;
                snakeAteSomething = true;
                score++;
            }
            snakefoods = snakefoods.next;
        }
        if (!snakeAteSomething) {
            snakeParts.removeFirst();
        }
        autoplay(x,y,c,snakeParts,snakeFood);
    }



    /**
     * Manually play the snake game!
     * Press 'w' to move up
     * Press 'a' to move left
     * Press 's' to move down
     * Press 'd' to move right
     * @param x : current x coordinate of the snake's head
     * @param y : current y coordinate of the snake's head
     * @param c : current direction of the snake represented by a char
     * @param snakeParts : the snake's Linked List
     * @param snakeFood : the snake food's Linked List
     */
    public static void manualPlay(double x, double y, char c, List snakeParts, List snakeFood){
        pause(speed);
        //StdDraw.clear();

        StdDraw.setPenColor(0,0,255);
        StdDraw.text(0.50,1,"Score : "+score);
        StdDraw.setPenColor(0,0,0);

        Node snakehead = snakeParts.first.next;
        StdDraw.filledSquare(snakehead.x,snakehead.y,0.024);
        Node snakefoods = snakeFood.first.next;
        for (int i = 0 ; i < snakeFood.size ;i++){
            StdDraw.filledSquare(snakefoods.x,snakefoods.y,0.024);
            snakefoods = snakefoods.next;
        }
        while (snakehead!=null){
            if (snakehead.next!= null) StdDraw.filledSquare(snakehead.x,snakehead.y,0.024);
            else{
                StdDraw.setPenColor(0,0,255);
                StdDraw.filledSquare(snakehead.x,snakehead.y,0.024);
            }
            snakehead = snakehead.next;
        }
        // receives direction input from user ('w' 'a' 's' 'd')
        if(StdDraw.hasNextKeyTyped()){
            c = StdDraw.nextKeyTyped();
        }
        // incrementing coordinates according to the last key typed.
        if (c == 'w') y += 0.05;
        else if (c == 'a') x -= 0.05;
        else if (c == 's') y -= 0.05;
        else if (c == 'd') x += 0.05;

        if(!withBoundaries){
            if (x>1)x -= 1;
            else if (x<0)x += 1;
            else if (y>1)y -= 1;
            else if (y<0)y += 1;
        }
        //If the user opted to use boundaries, check if the snake crashed into the wall.
        else if ((x>1 || x<0 || y>1 || y<0)&& !automatic){
            System.out.println("YOU LOSE (Crashed into the wall)");
            System.out.println("Score = "+score);
            return;
        }
        //checking if the snake is crashing into itself
        Node crashTest = snakeParts.first.next.next;
        while (crashTest!=null){
            if (crashTest.x==x && crashTest.y==y){
                System.out.println("YOU LOSE (Crashed into yourself)");
                System.out.println("Score = "+score);
                return;
            }
            crashTest = crashTest.next;
        }
        //checking if the snake reached food. if it did, eat the food, increase score and extend the snake by 1 size.
        boolean snakeAteSomething = false;
        snakeParts.insertLast(x,y);
        snakefoods = snakeFood.first.next;
        for (int i = 0 ; i < snakeFood.size ; i++){
            if (Math.abs(snakefoods.x-x)<0.001 && Math.abs(snakefoods.y-y)<0.001){
                Node food = snakeFood(snakeParts);
                snakefoods.x = food.x;
                snakefoods.y = food.y;
                snakeAteSomething = true;
                score++;
            }
            snakefoods = snakefoods.next;
        }
        if (!snakeAteSomething) {
            snakeParts.removeFirst();
        }

        manualPlay(x,y,c,snakeParts,snakeFood);
    }

    public static void manualPlayEfficient(double x, double y, char c, List snakeParts, List snakeFood){
        drawSnake(snakeParts);
        drawFood(snakeFood);
        scoreDraw();
        manualPlayEfficient2(x,y,c,snakeParts,snakeFood);
    }
    public static void manualPlayEfficient2(double x, double y, char c, List snakeParts, List snakeFood){
        pause(speed);
        foodEaten = false;
        if(StdDraw.hasNextKeyTyped()){
            c = StdDraw.nextKeyTyped();
        }

        if (c == 'w') y += 0.05;
        else if (c == 'a') x -= 0.05;
        else if (c == 's') y -= 0.05;
        else if (c == 'd') x += 0.05;

        if(!withBoundaries){
            if (x>1.001)x -= 1.05;
            else if (x<-0.001)x += 1.05;
            else if (y>1.001)y -= 1.05;
            else if (y<-0.001)y += 1.05;
        }
        else if ((x>1 || x<0 || y>1 || y<0)&& !automatic){
            System.out.println("YOU LOSE (Crashed into the wall)");
            System.out.println("Score = "+score);
            return;
        }

        Node crashTest = snakeParts.first.next.next;
        while (crashTest!=null){
            if (Math.abs(crashTest.x-x)<0.001 && Math.abs(crashTest.y-y)<0.001){
                System.out.println("YOU LOSE (Crashed into yourself)");
                System.out.println("Score = "+score);
                return;
            }
            crashTest = crashTest.next;
        }
        Node snakefoods = snakeFood.first.next;
        while(snakefoods!=null && !foodEaten){
            if (Math.abs(snakefoods.x-x)<0.001 && Math.abs(snakefoods.y-y)<0.001){
                Node food = snakeFood(snakeParts);
                snakefoods.x = food.x;
                snakefoods.y = food.y;
                StdDraw.setPenColor(150,75,0);
                StdDraw.filledSquare(food.x,food.y,0.024);
                StdDraw.setPenColor(0,0,0);
                foodEaten = true;
                score++;
                scoreDraw();
            }
            snakefoods = snakefoods.next;
        }

        StdDraw.filledSquare(x,y,0.024);
        if(!foodEaten){
            Node iteration = snakeParts.first.next;
            StdDraw.setPenColor(255,255,255);
            StdDraw.filledSquare(iteration.x,iteration.y,0.026);
            StdDraw.setPenColor(0,0,0);
            snakeParts.removeFirst();
        }
        snakeParts.insertLast(x,y);
        StdDraw.filledSquare(x,y,0.024);
        manualPlayEfficient2(x,y,c,snakeParts,snakeFood);
    }


    public static void scoreDraw(){
        StdDraw.setPenColor(255,255,255);
        StdDraw.filledRectangle(0.5,1,0.1,0.018);

        StdDraw.setPenColor(0,0,255);
        StdDraw.text(0.50,1,"Score : "+score);
        StdDraw.setPenColor(0,0,0);
    }


    public static void drawSnake(List snakeParts){
        Node iteration = snakeParts.first.next;
        while(iteration.next!=null){
            StdDraw.filledSquare(iteration.x,iteration.y,0.024);
            iteration = iteration.next;
        }
        StdDraw.filledSquare(iteration.x,iteration.y,0.024);
    }

    public static void drawFood(List snakeFood){
        Node iteration = snakeFood.first.next;
        StdDraw.setPenColor(150,75,0);
        while(iteration!=null){
            StdDraw.filledSquare(iteration.x,iteration.y,0.024);
            iteration = iteration.next;
        }
        StdDraw.setPenColor(0,0,0);
    }

    /**
     * Returns a direction in which the snake can move to, given the boolean parameters.
     * Has more chance to continue going in the current direction of the snake rather than turning.
     * @param c : current direction
     * @param up : true if the snake can move upwards
     * @param left : true if the snake can move to the left
     * @param down : true if the snake can move downwards
     * @param right : true if the snake can move to the right
     * @return c: returns either 'w' 'a' 's' or 'd'.
     */

    public static char randChar(char c,boolean up,boolean left,boolean down,boolean right){
        double randomNum = Math.random();
        if (c == 'w'){
            if(randomNum<0.2 && !left) return 'a';
            else if(randomNum<0.4 && !right) return 'd';
            else if(up){
                if (!left) return 'a';
                else if (!right) return 'd';
            }

        }
        else if (c == 'a'){
            if(randomNum<0.2 && !down) return 's';
            else if(randomNum<0.4 && !up) return 'w';
            else if(left){
                if (!down) return 's';
                else if (!up) return 'w';
            }
        }
        else if (c == 's'){
            if(randomNum<0.2 && !left) return 'a';
            else if(randomNum<0.4 && !right) return 'd';
            else if(down){
                if (!right) return 'd';
                else if (!left) return 'a';
            }
        }
        else if (c == 'd'){
            if (randomNum<0.2 && !up) return 'w';
            else if(randomNum<0.4 && !down) return 's';
            else if(right){
                if (!up) return 'w';
                else if (!down) return 's';
            }
        }
        return c;
    }

    /**
     * Creates a valid snake food Node and returns it.
     * @param snake : The snake Linked List
     * @return Food node.
     */
    public static Node snakeFood(List snake){
        Node curr = snake.first.next;
        double x = ((int)(Math.random()*20))/20.0;
        double y = ((int)(Math.random()*20))/20.0;
        while(curr != null){
            if (Math.abs(curr.x-x)<0.001 && Math.abs(curr.y-y)<0.001){
                x = ((int)(Math.random()*20+1))/20.0;
                y = ((int)(Math.random()*20+1))/20.0;
                curr = snake.first.next;
            }
            curr = curr.next;
        }

        return new Node(x,y);
    }



/*  2 methods required to pause the snake for t ms
 */

    private static void validateNonnegative(double x, String name) {
        if (x < 0) throw new IllegalArgumentException(name + " negative");
    }

    /**
     * Used to "Freeze" the game's refreshing rate for t milliseconds.
     * @param t - milliseconds.
     */
    public static void pause(int t) {
        validateNonnegative(t, "t");
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

}
