
# Problem Solving
 ```
public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        if(s.isEmpty() || s.length() > 2000 || !s.matches("([a-z]*\\(([a-z]+)\\))*[a-z]*"))
        {
            System.out.println(" 1 < String length < 2000 , string only contains lower case english and parentheses and all parentheses are blanced");
        }
        else {
            Pattern p = Pattern.compile("\\((\\w+)\\)");
            StringBuilder x = new StringBuilder();
            x.append(s);
            Matcher m = p.matcher(x);
            while (m.find()) {
                String cc = StringUtils.substring(s, m.start() + 1, m.end() - 1);
                x.replace(m.start()+1, m.end()-1, StringUtils.reverse(cc));
            }
            System.out.println(x.toString());
        }
    }

}
  
  ```
## Output
![Problem Solving Output](./images/p1.png)  