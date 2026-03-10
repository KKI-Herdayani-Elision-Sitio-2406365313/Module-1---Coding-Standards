# MODULE 1

## Reflection 1
While building this module, I followed "Clean Code" rules to make sure my work is easy to use for users and make sure i write the codes in the most efficient way. In this module, the **feature names** are very **straightforward**, ensuring that anyone looking at the code understands exactly what each part does. Moreover in this module i make sure **each functionn do one job** making it **easier for me to fix** when there is an error or of there are modifications i want to make on the code. There are a also few logic that are pretty similiar on the code, so when making functions for this problems i made sure to **reuse the logic** for finding products instead of writing the same loop over and over. I also use **UUID** for the product IDs. The purpose is, that it is **safer** than using simple numbers and it make sure that when we delete or updating a products, we do it on the correct one.


---

## Reflection 2
#### **1. Unit Testing and Code Coverage**

After writing the unit tests, I feel much more **confident and secure** with my code because, the unit test will test my code and catches errors automatically before the app even runs. I believe when writing a unit test, we should write enough tests to **check every possible path** both when things go right and when things go wrong (like trying to find a product that isn't there). **Code Coverage** is a helpful way to see which parts of the code have been executed by tests, however **100% coverage does not mean the code is perfect**. Even with full coverage, there can still be **logical bugs** that the tests did not specifically check for.

#### **2. Testing Cleanliness**
Creating a new functional test suite by copying and pasting the setup from a previous one makes the code **"dirty" and harder to maintain**. This approach **reduces code quality** because it could make a **Code Duplication**, which violates the "Don't Repeat Yourself" principle. The main issue is that if the setup logic (like the browser configuration) changes, I would have to **manually update it in every single file**, which is not efficient and prone to mistakes. To improve this, I couuld use **Inheritance** by creating a BaseFunctionalTest class that handles the setup, so all other tests can simply reuse that logic. Or I could also use the **Page Object Model**, which keeps the test logic separate from the browser instructions, making the code much cleaner and easier to read.

# MODULE 2
## Reflection 1

#### **1. Code Quality Issues and Fixing Strategy**

During this exercise, I used **PMD analysis** to identify potential bugs and maintainability issues in my codebase. One significant issue found was that certain fields in the product model were not declared as **final**, which could lead to unintended data mutations. My strategy was to make the product data **final** to ensure **immutability**, which is a core principle of clean code.

#### **2. Achieving 100% Code Coverage**

In this project i improve my test case code coverage fto **100%**. My strategy involved creating and updating several test:

* 
**ProductServiceTest**: I implemented tests for setup, create, findAll, and specific edge cases like testFindByIdNotFound, testUpdate, and testDelete to cover all logical paths.
 
**ProductControllerTest**: I added functional-style unit tests for the web layer, including testCreateProductPage, testProductListPage, and error handling for non-existent products.

**Repository and Application Tests**: I updated ProductRepositoryTest to include null and empty ID checks, and ensured the EshopApplication itself was tested, bringing the total coverage to 100%.
Following the "Self-Testing Build" principle, these tests now run automatically to catch errors before they reach production.


---

## Reflection 2

#### **CI/CD Definition Assessment**

I believe my current implementation **meets the definition** of Continuous Integration and Continuous Deployment as described in the module.

1. **Continuous Integration (CI):** My workflow is triggered automatically on every **push and pull request**, utilizing **GitHub Actions** to run the full Gradle test suite and generate JaCoCo reports.


2. **Analyzing the Quality:** Beyond basic tests, I integrated **PMD** to automatically scan to fulfill  CI principle of maintaining the quality of the code using automaed tools.

# MODULE 3
## Reflection

### SOLID Principles

1. **SRP (Single Responsibility Principle):** CarController was separated from ProductController into its own file. Each controller now has one responsibility.
2. **OCP (Open/Closed Principle):** CarService is an interface and CarServiceImpl implements it. Create a new implementation class without modifying the existing CarServiceImpl.
3. **LSP (Liskov Substitution Principle):** CarServiceImpl implements all methods in CarService. Any code that depends on CarService can use CarServiceImpl.
4. **ISP (Interface Segregation Principle):** ProductService and CarService are separated so CarController only depends on CarService.
5. **DIP (Dependency Inversion Principle):** CarController depends on the CarService interface, not directly to CarServiceImpl. This means controller does not need to know the specific implementation details of the service.

---

### Advantages of Applying SOLID Principles

- **SRP**: Since CarController and ProductController are separated, changes to one will not affect the other. This makes the code easier to manage and debug.
- **OCP**: We can add new features by creating a new class without touching the existing code, so we do not risk breaking anything that already works.
- **LSP**: We can replace CarServiceImpl with another implementation and everything will still work as expected.
- **ISP**: CarController only knows about car operations and ProductController only knows about product operations, so neither is burdened with unnecessary methods.
- **DIP**: CarController depends on the CarService interface, so we can change the service implementation without touching the controller.

---

### Disadvantages of NOT Applying SOLID Principles

- **Without SRP**: If both controllers are in the same file, the code becomes long and messy. A change in one part can accidentally break the other.
- **Without OCP**: Every time we want to add a new feature, we have to change the existing code, which can introduce new bugs.
- **Without LSP**: If a new implementation does not behave the same way, the code that depends on it will break in unexpected ways.
- **Without ISP**: A controller would be forced to know about methods it does not need, making the code harder to understand.
- **Without DIP**: If a controller directly creates its own service object, it becomes tightly tied to one specific implementation, making it hard to change or test.

# MODULE 4
## Reflection
### Reflect based on Percival (2017) flective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”)
TDD means writing tests before writing the actual code, so the tests act as a guide for what the code should do. I found this useful because it helped me focus on one thing at a time and catch bugs early. However, I sometimes skipped ahead and wrote too much code at once instead of following the RED-GREEN-REFACTOR steps properly. Next time I will slow down and only write enough code to make one failing test pass before moving to the next.

### F.I.R.S.T. Principle Reflection
F.I.R.S.T. stands for Fast, Independent, Repeatable, Self-Validating, and Timely, which are the qualities that make a good unit test. Most of my tests are fast and repeatable since they run quickly and give the same result every time. However, some tests depend on other tests working correctly first which breaks the Independent principle, and there were times I wrote the implementation before the tests which breaks the Timely principle. Next time I will make each test set up its own data and always write the test before the implementation.



