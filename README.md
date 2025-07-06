## Image Mirror Tool

A simple Java tool that scans an input folder for images, creates horizontally mirrored copies, and saves them to an output directory in `.png` format.
##  Before You Start

Make sure to create a file named **`props.txt`** in the **root folder** of your project.  
This file tells the program where to look for the images and where to save the results.

### Example `props.txt` content:

INPUT: ./input
OUTPUT: ./output

- `INPUT:` → path to the folder with original images  
- `OUTPUT:` → path where mirrored images will be saved

>  If these folders don’t exist, the program will create the output folder automatically.

##  How It Works

The tool reads the `props.txt` file, finds all supported image formats in the input folder, mirrors them horizontally, and saves them as `.png` in the output folder.


##  Supported Image Formats

- `.jpg`, `.jpeg`
- `.png`
- `.bmp`
- `.gif`
- `.tif`
- `.webp`


##  How to Run

1. **Build** the project or use the provided `.jar` file.
2. Place `props.txt` in the same directory as the `.jar`.
3. Put your images inside the input folder you specified.
4. Run the program:


java -jar ImageMirrorTool.jar

Example Project Structure

/ImageMirrorTool
├── props.txt
├── input/
│   └── example.jpg
├── output/
├── ImageMirrorTool.jar
└── README.md

Console Example

┌─────────────────────────────┐
│ Input directory loaded:     │
│ ./input                     │
└─────────────────────────────┘

┌─────────────────────────────┐
│ Output directory loaded:    │
│ ./output                    │
└─────────────────────────────┘

┌─────────────────────────────┐
│ Found 1 image(s). Starting… │
└─────────────────────────────┘

┌──────────────────────────────┐
│ Processed: cat.jpg           │
└──────────────────────────────┘

┌────────────────────────────────────────┐
│ All supported images have been mirrored│
└────────────────────────────────────────┘
