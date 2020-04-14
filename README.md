# VC-Assignment
It is a simple application for Android built with Android Jetpack components including ViewModel, Livedata,
Data-Binding concepts, RXJava and other latest components.

# Basic setup
No setup is required in order to use it. Just Install the application and you are ready to roll.

# Features
- Handles No connection case.
- Used RecyclerView with ItemDecoration animation of my choice.
- Used Constraint Layout and made the Layout responsive to different type of resolutions and handle orientation changes.
- Used HttpUrlConnection for hitting the API.
- Formatted the Expire Date Field to “Ending in ‘n’ days” where n is the no of days. If the value is null then the field is not shown.
- Extracted image name from the URL as asked in the question.
- Wrote code in Java
- Infinite scrolling / Pagination (calls the same API again)
- Sort by expiryDate (ascending & descending order providing null expiryDate at the end of the list)
- Filter by isTrending and ItemHeadline.

- Followed Material design standards
- LiveData and DataBinding Methods used (Jetpack)
- No boilerplate code
- Managed the activity data using lifecycles
- MVVM
- Better usage of Background Threads using RXJava

# Permissions Used
- android.permission.INTERNET
- android.permission.ACCESS_NETWORK_STATE
- android.permission.CHANGE_NETWORK_STATE



