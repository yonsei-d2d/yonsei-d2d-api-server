import xml.etree.ElementTree as ET
import sys

def renumber_osm(input_file, output_file):
    # Load the OSM file
    tree = ET.parse(input_file)
    root = tree.getroot()

    # ID maps and counter
    node_id_map = {}
    way_id_map = {}
    relation_id_map = {}
    next_id = 1
    next_way_id = 1;
    next_node_id = 1;

    # Helper function to renumber IDs
    def renumber_id(obj_id, id_map):
        nonlocal next_id
        if obj_id not in id_map:
            id_map[obj_id] = next_id
            next_id += 1
        return id_map[obj_id]

    # Renumber nodes
    for node in root.findall('node'):
        old_id = int(node.get('id'))
        new_id = renumber_id(old_id, node_id_map)
        node.set('id', str(new_id))
    
    next_node_id = next_id
    next_id = 1

    # Renumber ways
    for way in root.findall('way'):
        old_id = int(way.get('id'))
        new_id = renumber_id(old_id, way_id_map)
        way.set('id', str(new_id))

        # Update way's node references
        for nd in way.findall('nd'):
            ref_id = int(nd.get('ref'))
            if ref_id in node_id_map:
                nd.set('ref', str(node_id_map[ref_id]))
    
    next_way_id = next_id
    next_id = 1
    
    # Renumber relations
    for relation in root.findall('relation'):
        old_id = int(relation.get('id'))
        new_id = renumber_id(old_id, relation_id_map)
        relation.set('id', str(new_id))

        
    
    next_rel_id = next_id
    next_id = 1

    # Nested Relations
    for relation in root.findall('relation'):
        old_id = int(relation.get('id'))
        new_id = renumber_id(old_id, relation_id_map)
        relation.set('id', str(new_id))

        # Update member references
        for member in relation.findall('member'):
            ref_id = int(member.get('ref'))
            member_type = member.get('type')
            if member_type == 'node':
                if ref_id in node_id_map:
                    member.set('ref', str(node_id_map[ref_id]))
                else:
                    member.set('ref', str(next_node_id))
                    node_id_map[ref_id] = next_node_id
                    next_node_id += 1
            elif member_type == 'way':
                if ref_id in way_id_map:
                    member.set('ref', str(way_id_map[ref_id]))
                else:
                    member.set('ref', str(next_way_id))
                    way_id_map[ref_id] = next_way_id
                    next_way_id += 1
            elif member_type == 'relation':
                if ref_id in relation_id_map:
                    member.set('ref', str(relation_id_map[ref_id]))
                else:
                    member.set('ref', str(next_rel_id))
                    relation_id_map[ref_id] = next_rel_id
                    next_rel_id += 1

    # Save the renumbered OSM data to the output file
    tree.write(output_file, encoding='utf-8', xml_declaration=True)
    print(f"Renumbered OSM file saved as {output_file}")

if __name__ == "__main__":
    # Check for correct number of arguments
    if len(sys.argv) != 3:
        print("Usage: python renumber_osm.py <input_file> <output_file>")
        sys.exit(1)

    # Get file names from arguments
    input_file = sys.argv[1]
    output_file = sys.argv[2]

    # Run the renumbering process
    renumber_osm(input_file, output_file)
