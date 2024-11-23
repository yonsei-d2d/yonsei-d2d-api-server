import xml.etree.ElementTree as ET
import sys
import csv

if len(sys.argv) != 2:
    print("Usage: python script.py <path_to_xml_file>")
    sys.exit(1)

file_path = sys.argv[1]



locationList = []

locationParsedList = []

def saveCSV(filename, data, fieldnames):
    with open(filename, mode="w", newline="", encoding="utf-8") as file:
        writer = csv.DictWriter(file, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(data)


try:
    print("Searching target nodes...")
    tree = ET.parse(file_path)
    root = tree.getroot()
    for node in root.findall('.//node'):
        for tag in node.findall('tag'):
            if tag.get('k') == 'location_type':
                locationList.append(node)

    print(f"{len(locationList)} location nodes found.")

    print("Extracting target node information...")
    
    # Location Extraction
    for node in locationList:
        location = {}
        location["node_id"] = node.get('id')
        location["latitude"] = node.get('lat')
        location["longitude"] = node.get('lon')
        location["level"] = 0
        for tag in node.findall('tag'):
            if tag.get('k') == 'location_type':
                location["type"] = tag.get('v')
                continue
            if tag.get('k') == 'level':
                location["level"] = tag.get('v')
                continue
            if tag.get('k') == 'floor':
                location["floor"] = tag.get('v')
                continue
            if tag.get('k') == 'name':
                location["name"] = tag.get('v')
                continue
        locationParsedList.append(location);
    
    print("Savind Data...")

    saveCSV("location.csv", locationParsedList, ["node_id", "latitude", "longitude", "name", "type", "level", "floor"])

    print("Success!")

except FileNotFoundError:
    print(f"File not found: {file_path}")
except ET.ParseError:
    print(f"Error parsing the XML file: {file_path}")
