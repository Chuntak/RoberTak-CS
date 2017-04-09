<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Syllabus</title>
</head>
<body>
<p>This is syllabus</p>

<p>Select a file to upload to your Google Cloud Storage bucket.</p>
<div ng-controller="syllabusCtrl">
    <form ng-submit="uploadSyllabus()">
        <input type="file" file-model="myFile"/>
        <button type="submit">Submit</button>
    </form>
</div>
</body>



</html>