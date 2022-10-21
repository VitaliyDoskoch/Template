# Template

It is a learning project designed to accumulate knowledge for easy access in the future. The project is implemented using MVI architecture
pattern based on the MVVM pattern.

## The project consists of these specific modules:

1. database - contains the whole database schema, including RoomDatabase, Daos, Entities, Views, and Embedded objects.
2. api - contains api-related classes, services, request and response models.
3. core - contains stuff, that must be shared across features, such as custom views, basic abstractions, common UseCases, common
   resources (such as dimens/themes), strings, icons and drawables.
4. feature-modules - contain feature-related stuff, including Mappers, UseCases, Screens, and ViewModels.
5. app - the main module that wires up all other modules. Contains cross-feature stuff, Screen containers, and the Navigation schema.
6. legacy - optional module for legacy code that you don't want to delete yet.
7. optional wrapper modules for third-party libraries - are used to decrease the influence by the library-specific code. Designed to
   decrease the impact of the migration to a different third-party library.

## Coding conventions:

- Common:
    1. All fields of data classes should be marked with the 'val' keyword.

- Resources:
    1. Icons that could be gracefully tinted should be named with the 'ic_' prefix. Multi-colored icons and webp images should be named with
       the 'im_' prefix.
    2. Strings should be named according to the corresponding feature.
    3. All theme-related resources should be accessed via the theme, not directly.

- Database:
    1. An Entity could contain a subset nested class. The subset class represents the same entity, but it doesn't contain some of its
       fields.
    2. A Dao should be an abstract class that implements the BaseDao. All of its methods should be marked with suspend, except those that
       return a Flow.
    3. A regular Dao should not reference multiple tables. Create a special separated Dao class to perform actions with multiple tables.

- MVI:
    1. State is used for View <-> Vm communication.
    2. The View must display error messages using CoreError only and must not work with Exception directly.
    3. The View must work with uiModel objects and must not work with database or api objects directly.
    4. The navigation is performed by the Navigator via a ViewModel.

## Naming conventions:

- Database:
    1. An Entity should be named starting with the 'Db' prefix
    2. An Embedded object of an Entity should be named starting with the 'Dbe' prefix
    3. The naming of a subset class of an Entity should end with the 'Subset' suffix
    4. The name of the Dao starts with the name of the corresponding Entity and ends with the 'Dao' suffix

- Api:
    1. The name of a request model should end with the 'Request' suffix.
    2. The name of a response model should end with the 'Response' suffix.
    3. The name of a Service should end with the 'Service' suffix.
    4. The name of a transitive model should end with the 'Dto' suffix.

## License

[MIT](https://choosealicense.com/licenses/mit/)