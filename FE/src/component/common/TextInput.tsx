interface Props {
  placeholder?: string;
}

const TextInput = ({ placeholder = "" }: Props) => {
  return <input type="text" placeholder={placeholder} className=" border border-solid border-gray-300 rounded-md" />;
};

export default TextInput;
