
## Reflection 1
While building this module, I followed "Clean Code" rules to make sure my work is easy to use for users and make sure i write the codes in the most efficient way. In this module, the **feature names** are very **straightforward**, ensuring that anyone looking at the code understands exactly what each part does. Moreover in this module i make sure **each functionn do one job** making it **easier for me to fix** when there is an error or of there are modifications i want to make on the code. There are a also few logic that are pretty similiar on the code, so when making functions for this problems i made sure to **reuse the logic** for finding products instead of writing the same loop over and over. I also use **UUID** for the product IDs. The purpose is, that it is **safer** than using simple numbers and it make sure that when we delete or updating a products, we do it on the correct one.


---

## Reflection 2
#### **1. Unit Testing and Code Coverage**

After writing the unit tests, I feel much more **confident and secure** with my code because, the unit test will test my code and catches errors automatically before the app even runs. I believe when writing a unit test, we should write enough tests to **check every possible path** both when things go right and when things go wrong (like trying to find a product that isn't there). **Code Coverage** is a helpful way to see which parts of the code have been executed by tests, however **100% coverage does not mean the code is perfect**. Even with full coverage, there can still be **logical bugs** that the tests did not specifically check for.

#### **2. Testing Cleanliness**
Creating a new functional test suite by copying and pasting the setup from a previous one makes the code **"dirty" and harder to maintain**. This approach **reduces code quality** because it could make a **Code Duplication**, which violates the "Don't Repeat Yourself" principle. The main issue is that if the setup logic (like the browser configuration) changes, I would have to **manually update it in every single file**, which is not efficient and prone to mistakes. To improve this, I couuld use **Inheritance** by creating a BaseFunctionalTest class that handles the setup, so all other tests can simply reuse that logic. Or I could also use the **Page Object Model**, which keeps the test logic separate from the browser instructions, making the code much cleaner and easier to read.

