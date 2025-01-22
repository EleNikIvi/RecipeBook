## The "Recipe Book" 

The mobile application is designed to provide a user-friendly and efficient platform for storing, organizing, and managing recipes. It adheres to modern software development principles and best practices, ensuring a robust, scalable, and maintainable codebase.

<details>
<summary>Libraries and tools</summary>
 
#### Architecture and Design Patterns:

`"Clean Architecture"` with separate layers - `Data`, `Domain` and `UI`. Domain layer communicates with Data layer using abstraction of Repository interface.

#### MVVM and MVI Patterns:
The application employs the `MVVM` (Model-View-ViewModel) and `MVI` (Model-View-Intent) patterns to effectively decouple the model, view, and business logic layers. This separation enhances code clarity, flexibility, and testability.

#### Jetpack Libraries:
The application leverages the Jetpack suite of libraries from Google, recommended for building modern Android apps. These libraries include:
*	`Jetpack Compose`: A declarative UI toolkit for creating flexible, performant, and visually appealing interfaces.
*	`Navigation-compose`: A navigation library that simplifies navigation between app screens.
*	`Hilt`: A dependency injection tool.
*	`Room`: For local database.

#### Design System:
To establish a consistent and user-friendly interface, the application utilizes a `Design System`. It contains a custom Theme with custom `Colors`, `Typography`, `UI components`, etc.

#### Programming Language
`Kotlin`
#### Key Features
*	*Recipe Management*: Store recipes in text, image, or link formats.
*	*Categorization and Filtering*: Organize recipes into categories and filter them for easy retrieval.
*	*Language Translation*: Translate recipes between Ukrainian and English languages.
*	*Shopping List Creation*: Generate shopping lists based on selected recipes.
*	*Shopping List Management*: Edit and mark purchased items on shopping lists.

#### Minimum Android API Version: 28+

#### Additional Features
*	Recipe Search: Search for recipes by name and ingredients.
*	Category Management: Create and manage recipe categories.
*	Recipe Image Editing: Modify recipe images to enhance visual appeal.
*	Shopping List Editing and Deletion: Edit and delete shopping lists as needed.
*	Custom Ingredient Addition: Add custom ingredients to recipes and shopping lists.
*	Recipe Recommendations: Receive recipe recommendations based on cooking history.
#### Data Storage
Created recipes and shopping lists are stored in a local database using `Room`, a library specifically designed for working with SQLite databases in Android apps. Room simplifies database interactions and ensures data persistence. The following operations are used for managing data:
*	*Create/Update Record*: Inserts or updates a record in the database.
*	*Get List of Records*: Retrieves a list of records from the database based on specified criteria.
*	*Get Record by ID*: Fetches a specific record from the database based on its unique identifier.
*	*Delete Record*: Removes a record from the database.

</details>

<details>
<summary>Screens description</summary>
 
#### 1 Main Screen
*	Displays a scrollable list of recipes.
*	Features a toolbar with:
     - A search field for filtering recipes.
     - A button for creating new recipes.
     - A category filter for organizing recipes.
*	Upon tapping a recipe:
     - Navigates to the recipe details screen.
     - On a long press:
*	Displays options to edit or delete the recipe.
#### 2 Recipe Creation/Editing Screen
*	Allows users to add images, names, categories, descriptions, and ingredients to recipes.
*	Provides a list of existing categories for selection or the option to create new ones.
*	Enables users to modify recipe images.
* Saves recipes upon entering a recipe name.
#### 3 Recipe Details Screen
*	Presents the recipe's image, name, category, description, and ingredients.
*	Includes buttons for:
     - Editing the recipe (navigates to the editing screen).
     - Creating a shopping list (displays a dialog for selecting a new or existing list).
#### 4 Create New Category Screen
*	Provides a field for entering the category name.
*	Features a "Save" button that becomes active upon entering a category name.
#### 5 Create/Edit Shopping List Screen
*	Includes a field for entering the shopping list name.
*	Offers fields for adding ingredients (with auto-population from recipes).
*	Enables users to modify, delete, and add new ingredients.
#### 6 Shopping Lists Screen
*	Features a toolbar with a search field.
*	Provides a button for creating new shopping lists.
*	Displays a list of shopping lists, showing the list name and the first four ingredients.
*	Navigates to the shopping list details screen upon tapping
#### 7 Shopping List Details Screen
*	Displays a list of items in the shopping list.
*	Provides a checkbox next to each item to mark it as purchased.
*	Includes an "Edit" button that navigates to the shopping list editing screen.
</details>
 
