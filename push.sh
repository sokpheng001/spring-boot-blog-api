git add .
read -p "[+] Commit message: " msg;
git commit -m "$msg";
echo -m "Current branch: "
git branch
echo "Are you sure to push?..."
read
git push -u origin main 

