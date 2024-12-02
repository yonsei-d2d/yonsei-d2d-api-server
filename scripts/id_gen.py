import xml.etree.ElementTree as ET
import sys
import csv
import uuid

if len(sys.argv) != 3:
    print("Usage: python script.py <path_to_xml_file> <path_to_new_xml_file>")
    sys.exit(1)

file_path = sys.argv[1]
out_file_path = sys.argv[2]

def generate_uuid(namespace: str, name: str) -> uuid.UUID:
    namespace_uuid = uuid.uuid5(uuid.NAMESPACE_DNS, namespace)
    return uuid.uuid5(namespace_uuid, name)

def validate_uuid(namespace: str, name: str, uuid_to_validate: str) -> bool:
    try:
        expected_uuid = generate_uuid(namespace, name)
        return str(expected_uuid) == uuid_to_validate
    except ValueError:
        return False

locationList = []
locationParsedList = []
namespace = "location_type"

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

    print("Generating target node ID...")
    
    # Location Extraction
    for node in locationList:
        name = None;
        sub_id = "0";
        targetTag = None;
        for tag in node.findall('tag'):
            if tag.get('k') == 'db_id':
                targetTag = tag
            if tag.get('k') == 'name':
                name = tag.get('v')
            if tag.get('k') == 'sub_id':
                sub_id = tag.get('v')
        if (targetTag == None):
            if (name == None):
                print(f"[ERROR] NodeID {node.get('id')} has marked as location but missing name")
            else:
                print(f"[INFO] Generating ID for NodeID {node.get('id')} Name {name}")
                new_id = str(generate_uuid(namespace, name+sub_id))
                child = ET.SubElement(node, "tag")
                child.set('k', 'db_id')
                child.set('v', new_id)
        else:
            if (validate_uuid(namespace, name+sub_id, targetTag.get('v'))):
                print(f"[INFO] NodeID {node.get('id')} Name {name} is valid location id")
            else:
                print(f"[ERROR] NodeID {node.get('id')} has incorrect uuid")
            
    
    print("Savind Data...")
    tree.write(out_file_path, encoding='utf-8', xml_declaration=True)
    print(f"ID OSM file saved")

    print("Success!")

except FileNotFoundError:
    print(f"File not found: {file_path}")
except ET.ParseError:
    print(f"Error parsing the XML file: {file_path}")
