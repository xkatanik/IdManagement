import json


def load_rules(rules_file):
    """
    Load config file and return dictionary
    :param rules_file: path to the file containing rules
    :return: Dictionary containing rules. Raises TypeError if the given file could not be load as json.
    """
    with open(rules_file, "r") as config_file:
        try:
            rules = json.load(config_file)
        except json.decoder.JSONDecodeError:
            raise TypeError("{0} is not valid json file".format(rules_file))

        return rules


def validate_link(left_system, left_type, right_system, right_type):
    """
    Checks for the validity of the link between given generic objects. Does _not_ checks validity of the objects itself.

    :param left_system: System of the generic object (GO) on the left side of the link
    :param left_type: Type of the GO on the left side of the link
    :param right_system: System of the GO on the right side of the link
    :param right_type: Type of the GO on the right side of the link
    :param rules: Dictionary containing the rules form the edges_config.json
    :return: True, if link is allowed. Raises TypeError otherwise.
    """
    rules = load_rules("edges_config.json")
    left = left_system + "." + left_type
    print(left)
    right = right_system + "." + right_type
    print(right)

    if right in rules[left]:
        return True
    else:
        raise TypeError("Generic object {0} is not allowed to be linked with {1}", right, left)


def validate_generic_object(gen_object_system, gen_object_type):
    """
    Check the validity of single generic object

    :param gen_object_system: System of the generic object.
    :param gen_object_type: Type of the generic object.
    :param rules: Dictionary containing the rules from the generic_object_config.json file.
    :return: True if the generic object has valid type in given system. Raises TypeError otherwise.
    """
    rules = load_rules("generic_object_config.json")
    if gen_object_type in rules[gen_object_system]["allowed"]:
        return True
    else:
        raise TypeError("Type {0} is not allowed in system {1}".format(gen_object_type, gen_object_system))


# def test():
#     gen_obj_rules = load_rules("generic_object_config.json")
#     edges_rules = load_rules("edges_config.json")
#
#     assert validate_generic_object("CELSPACAdmin", "family", gen_obj_rules)
#     assert validate_generic_object("CladeIS", "user", gen_obj_rules)
#     assert validate_generic_object("no-system", "experiment", gen_obj_rules)
#     # assert validate_generic_object("OpenSpecimen", "subject", gen_obj_rules)
#
#
#     assert validate_link("CELSPACAdmin", "proband", "OpenSpecimen", "proband", edges_rules)
#     assert validate_link("CELSPACAdmin", "proband", "CladeIS", "subject", edges_rules)
#
#
# if __name__ == '__main__':
#     test()
