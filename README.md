# Pokepedia
Android mobile app that provides a list of pokemon for each generation. It allows the user to view the traits, moves and illustrations for each pokemon. You can download the APK of the app here: [download link](https://docs.google.com/uc?export=download&id=1DsQSewRhpmZGH0ZNRs7c8MUflPGL7JKh)

## Application process
The backend is written in Kotlin and the frontend is written in XML. It consists of one activity and four fragments. It retrieves information about every pokemon in each generation from the RESTful Pokemon API in [pokeapi.co](https://pokeapi.co). The application uses Retrofit and Moshi to perform HTTP GET requests from the API and parse the JSON responses into Kotlin objects. To load the large amount of pictures efficiently from the API, the Coil library was used.

## Demo
The application starts with the Home tab where the user is shown a list of all pokemon in a generation. Like the image below:
<p align="center">
 <img src="https://github.com/ricardoliu99/Pokepedia/blob/master/examples/home_tab.jpg?raw=true" height="450">
</p>

This tab shows the first generation by default, but the user can choose another generation from the dropdown list as shown below:
<p align="center">
 <img src="https://github.com/ricardoliu99/Pokepedia/blob/master/examples/generation_selection.jpg?raw=true" height="450">
</p>

At the top of this tab, the user can press the search button and a search bar will appear. As the user types, the pokemon list shown gets filtered with contents matching the user's input. For example, typing "ab" will show the following filtered list:
<p align="center">
 <img src="https://github.com/ricardoliu99/Pokepedia/blob/master/examples/search_example.jpg?raw=true" height="450">
</p>

The user can either type the full name of a pokemon and press the search button, or they can press on the desired pokemon in the list. Then, the user will be directed to another screen containing three tabs: Traits, Moves and Illustrations. The bottom navigation bar allows the user to change among these three tabs and the home button at the top will direct the user back to the Home tab from before.

The Traits tab contains the name of the pokemon and the pokemon image. It also contains a table with the basic traits of the pokemon and a bar graph showing the base stats from the pokemon. The image below shows an example of this.
<p align="center">
 <img src="https://github.com/ricardoliu99/Pokepedia/blob/master/examples/traits_tab.jpg?raw=true" height="450">
</p>

The Moves tab contains detailed information about each possible move which the pokemon can obtain. If the text in a row is too long, the text will be sliding in and out. The user can press on any of the headers of the table to sort the moves in numerical/alphabetical ascending order. The following image shows this tab:
<p align="center">
 <img src="https://github.com/ricardoliu99/Pokepedia/blob/master/examples/moves_tab.jpg?raw=true" height="450">
</p>

The Illustrations tab contains four images: the default front image, the default back image, the shiny front image and the shiny back image of the selected pokemon. For example, the image below shows this:
<p align="center">
 <img src="https://github.com/ricardoliu99/Pokepedia/blob/master/examples/illustrations_tab.jpg?raw=true" height="450">
</p>
