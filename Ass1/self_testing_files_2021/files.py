import filecmp
print(filecmp.cmp('./c_l','./cipher_long'))


with open('./c', 'r') as file1:
    with open('./cipher_short', 'r') as file2:
        same = set(file1).intersection(file2)

same.discard('\n')

with open('s.txt', 'w') as file_out:
    for line in same:
        file_out.write(line)