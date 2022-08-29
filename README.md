# Template

It is a learning project designed to accumulate knowledge for easy-access in the future. The project is implemented using MVI architecture
pattern based on the MVVM pattern.

> NOTE: The current implementation of the project differs from the stated below!

## The project consists of these specific modules:

1. database - contains the whole database schema, including RoomDatabase, Daos, Entities, Views, and Embedded objects.
2. api - contains api-related classes, services, request and response models.
3. core - contains stuff, shared across features, such as Repositories, custom views, basic abstractions, common UseCases, common
   resources (such as dimens/themes), all icons and drawables except rare feature-specific images.
4. feature-modules - contain feature-related stuff, including strings, feature-specific resources, Mappers, UseCases, Screens, and
   ViewModels. Cross-feature UseCases and the Navigator should be injected from the app module.
5. optional wrapper modules for third-party libraries - are used to decrease the influence of the library-specific code. Designed to
   decrease the impact of the migration to a different third-party library.
6. app - the main module that wires up all modules. Contains cross-feature stuff, Screen containers, and the Navigation schema.

## Coding conventions:

- Common:
    1. All fields of data classes should be marked with the 'val' keyword.

- Resources:
    1. Icons that could be gracefully tinted should be named with the 'ic_' prefix. Multi-colored icons and webp images should be named with
       the 'im_' prefix.
    2. Strings should be named according to the corresponding feature.
    3. Content descriptions should be placed in a separate file.
    4. All theme-related resources should be accessed via the theme, not directly.

- Database:
    1. An Entity should implement the BaseEntity that has the 'rowId' PrimaryKey
    2. An Entity could contain a subset nested class. The subset class represents the same entity, but it doesn't contain some of its
       fields.
    3. A Dao should be an abstract class that implements the BaseDao. All of its methods should be marked with suspend, except those that
       return a Flow.
    4. A regular Dao should not reference multiple tables. Create a special separated Dao class to perform actions with multiple tables.

- Misc:
    1. For VM -> View communication use State. State.Error should provide a CoreError, not an Exception.
    2. The navigation is performed via the Navigator within a ViewModel.

## Naming conventions:

- Database:
    1. An Entity should be named starting with the 'Db' prefix
    2. An Embedded object of an Entity should be named starting with the 'Dbe' prefix
    3. A subset class of an Entity should be named ending with the 'Subset' suffix
    4. The name of the Dao starts with the name of the corresponding Entity and ends with the 'Dao' suffix

- Api:
    1. Request models should be named ending with the 'Request' suffix.
    2. Response models should be named ending with the 'Response' suffix.
    3. Services should be named ending with the 'Service' suffix.
    4. Transitive models should be named ending with the 'Dto' suffix.

## License

[MIT](https://choosealicense.com/licenses/mit/)